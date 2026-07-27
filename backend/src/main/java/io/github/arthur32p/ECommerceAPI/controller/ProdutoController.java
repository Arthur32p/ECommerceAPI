package io.github.arthur32p.ECommerceAPI.controller;

import io.github.arthur32p.ECommerceAPI.controller.common.GenericController;
import io.github.arthur32p.ECommerceAPI.dto.ProdutoRequestDto;
import io.github.arthur32p.ECommerceAPI.dto.ProdutoResponseDto;
import io.github.arthur32p.ECommerceAPI.service.ProdutoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProdutoController implements GenericController {

    private final ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<Page<ProdutoResponseDto>> listarTodos(
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {

        Page<ProdutoResponseDto> produtos = produtoService.listarTodos(pageable);
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ProdutoResponseDto>> buscarPorNome(
            @RequestParam String nome,
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {

        Page<ProdutoResponseDto> produtos = produtoService.buscarPorNome(nome, pageable);
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDto> buscarProduto(@PathVariable UUID id){
        return ResponseEntity.ok(produtoService.buscarProduto(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProdutoResponseDto> criarProduto(@RequestBody @Valid ProdutoRequestDto dto){
        ProdutoResponseDto saved = produtoService.create(dto);
        URI location = gerarHeaderLocation(saved.id());

        return ResponseEntity.created(location).body(saved);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProdutoResponseDto> atualizarProduto(@PathVariable UUID id, @RequestBody @Valid ProdutoRequestDto dto){
        return ResponseEntity.ok(produtoService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletarProduto(@PathVariable UUID id) {
        produtoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/toggle-status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> alternarStatusProduto(@PathVariable UUID id) {
        produtoService.alternarStatus(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/{id}/imagem", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProdutoResponseDto> uploadImagem(
            @PathVariable UUID id,
            @RequestParam("file") MultipartFile file) {

        ProdutoResponseDto produtoAtualizado = produtoService.uploadImagem(id, file);
        return ResponseEntity.ok(produtoAtualizado);
    }
}
