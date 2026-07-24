import { Component, OnInit, signal } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css'
})
export class Dashboard implements OnInit {
  tokenSalvo = signal<string | null>('');

  constructor(private router: Router) {}

  ngOnInit(): void {
    const token = localStorage.getItem('token');
    this.tokenSalvo.set(token);
  }

  logout(): void {
    localStorage.removeItem('token');
    this.router.navigate(['/login']);
  }
}