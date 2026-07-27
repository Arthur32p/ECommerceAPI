package io.github.arthur32p.ECommerceAPI.service;

import io.github.arthur32p.ECommerceAPI.dto.ProdutoRequestDto;
import io.github.arthur32p.ECommerceAPI.dto.ProdutoResponseDto;
import io.github.arthur32p.ECommerceAPI.mapper.ProdutoMapper;
import io.github.arthur32p.ECommerceAPI.model.Produto;
import io.github.arthur32p.ECommerceAPI.repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final ProdutoMapper produtoMapper;
    private final StorageService storageService;

    public ProdutoResponseDto create(ProdutoRequestDto dto){
        Produto produto = produtoMapper.toEntity(dto);
        return produtoMapper.toResponse(produtoRepository.save(produto));
    }

    public Page<ProdutoResponseDto> listarTodos(Pageable pageable){
        return produtoRepository
                .findAll(pageable)
                .map(produtoMapper::toResponse);
    }

    public Page<ProdutoResponseDto> buscarPorNome(String nome, Pageable pageable) {
        return produtoRepository
                .findByNomeContainingIgnoreCase(nome, pageable)
                .map(produtoMapper::toResponse);
    }

    public ProdutoResponseDto buscarProduto(UUID id) {
        return produtoMapper.toResponse(produtoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Produto não encontrado")));
    }

    @Transactional
    public ProdutoResponseDto update(UUID id, ProdutoRequestDto dto){
        Produto produto = produtoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com o ID: " + id));

        produto.setNome(dto.nome());
        produto.setDescricao(dto.descricao());
        produto.setPreco(dto.preco());
        produto.setQuantidadeEstoque(dto.quantidadeEstoque());

        return produtoMapper.toResponse(produtoRepository.save(produto));

    }

    @Transactional
    public void delete(UUID id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com o ID: " + id));

        produto.setAtivo(false);
    }

    @Transactional
    public void alternarStatus(UUID id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com o ID: " + id));

        produto.setAtivo(!produto.getAtivo());
    }

    public ProdutoResponseDto uploadImagem(UUID id, MultipartFile file) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        String imagemUrl = storageService.uploadFile(file);

        produto.setImagemUrl(imagemUrl);
        Produto produtoSalvo = produtoRepository.save(produto);

        return produtoMapper.toResponse(produtoSalvo);
    }

}
