import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';
import { TagModule } from 'primeng/tag';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, RouterModule, CardModule, ButtonModule, TagModule],
  template: `
    <div class="min-h-screen bg-gradient-to-br from-blue-50 to-indigo-100 flex items-center justify-center p-4">
      <div class="max-w-4xl w-full">
        <!-- Header Card -->
        <p-card class="mb-6 shadow-lg">
          <div class="text-center">
            <div class="flex justify-center mb-4">
              <i class="pi pi-check-circle text-6xl text-green-500"></i>
            </div>
            <h1 class="text-4xl font-bold text-gray-800 mb-2">
              UKNF Report Management System
            </h1>
            <p class="text-lg text-gray-600 mb-4">
              Secure file upload and validation system for supervised entities
            </p>
            <p-tag value="Technology Stack" severity="info" class="mr-2"></p-tag>
            <p-tag value="WCAG 2.2 Compliant" severity="success"></p-tag>
          </div>
        </p-card>

        <!-- Features Grid -->
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 mb-6">
          <!-- File Upload Card -->
          <p-card class="hover:shadow-xl transition-shadow duration-300">
            <div class="text-center">
              <i class="pi pi-upload text-4xl text-green-500 mb-3"></i>
              <h3 class="text-xl font-semibold text-gray-800 mb-2">File Upload</h3>
              <p class="text-gray-600 text-sm">
                Upload XLSX report files with automatic validation and status tracking.
              </p>
            </div>
          </p-card>

          <!-- Validation Card -->
          <p-card class="hover:shadow-xl transition-shadow duration-300">
            <div class="text-center">
              <i class="pi pi-check-circle text-4xl text-blue-500 mb-3"></i>
              <h3 class="text-xl font-semibold text-gray-800 mb-2">Auto Validation</h3>
              <p class="text-gray-600 text-sm">
                Automatic validation with real-time status updates and error reporting.
              </p>
            </div>
          </p-card>

          <!-- Security Card -->
          <p-card class="hover:shadow-xl transition-shadow duration-300">
            <div class="text-center">
              <i class="pi pi-shield text-4xl text-purple-500 mb-3"></i>
              <h3 class="text-xl font-semibold text-gray-800 mb-2">Secure Processing</h3>
              <p class="text-gray-600 text-sm">
                Secure file handling with encryption and comprehensive audit trails.
              </p>
            </div>
          </p-card>
        </div>

        <!-- Action Buttons -->
        <div class="flex flex-wrap justify-center gap-4 mb-6">
          <p-button 
            label="Upload Report" 
            icon="pi pi-upload" 
            severity="success"
            size="large"
            routerLink="/upload"
            class="px-6 py-3">
          </p-button>
          <p-button 
            label="View Reports" 
            icon="pi pi-list" 
            severity="info"
            size="large"
            class="px-6 py-3">
          </p-button>
          <p-button 
            label="Documentation" 
            icon="pi pi-book" 
            severity="secondary"
            size="large"
            class="px-6 py-3">
          </p-button>
        </div>

        <!-- Status Panel -->
        <p-card>
          <div class="text-center">
            <h3 class="text-xl font-semibold text-gray-800 mb-4">System Status</h3>
            <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
              <div class="flex items-center justify-center p-4 bg-green-50 rounded-lg">
                <i class="pi pi-check text-green-500 text-2xl mr-3"></i>
                <div class="text-left">
                  <div class="font-semibold text-green-800">Frontend Ready</div>
                  <div class="text-sm text-green-600">Angular + PrimeNG + Tailwind</div>
                </div>
              </div>
              <div class="flex items-center justify-center p-4 bg-blue-50 rounded-lg">
                <i class="pi pi-cog text-blue-500 text-2xl mr-3"></i>
                <div class="text-left">
                  <div class="font-semibold text-blue-800">Backend Ready</div>
                  <div class="text-sm text-blue-600">Spring Boot + JPA</div>
                </div>
              </div>
              <div class="flex items-center justify-center p-4 bg-purple-50 rounded-lg">
                <i class="pi pi-shield text-purple-500 text-2xl mr-3"></i>
                <div class="text-left">
                  <div class="font-semibold text-purple-800">Security Ready</div>
                  <div class="text-sm text-purple-600">OAuth2 + JWT</div>
                </div>
              </div>
            </div>
          </div>
        </p-card>
      </div>
    </div>
  `,
  styles: []
})
export class HomeComponent {}

