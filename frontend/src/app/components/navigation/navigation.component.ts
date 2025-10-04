import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';

@Component({
  selector: 'app-navigation',
  standalone: true,
  imports: [CommonModule, RouterModule, ButtonModule, CardModule],
  template: `
    <nav class="bg-white shadow-lg border-b">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="flex justify-between h-16">
          <div class="flex items-center">
            <div class="flex-shrink-0">
              <h1 class="text-xl font-bold text-gray-800">UKNF Report System</h1>
            </div>
          </div>
          <div class="flex items-center space-x-4">
            <p-button 
              label="File Upload" 
              icon="pi pi-upload"
              routerLink="/upload"
              severity="success"
              size="small">
            </p-button>
            <p-button 
              label="Home" 
              icon="pi pi-home"
              routerLink="/"
              severity="secondary"
              size="small">
            </p-button>
          </div>
        </div>
      </div>
    </nav>
  `,
  styles: []
})
export class NavigationComponent {}

