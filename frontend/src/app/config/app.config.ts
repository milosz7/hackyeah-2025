import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AppConfig {
  // Backend API configuration
  private readonly API_BASE_URL = 'http://localhost:8080/api';
  
  // Get the full API URL for a specific endpoint
  getApiUrl(endpoint: string): string {
    return `${this.API_BASE_URL}${endpoint}`;
  }
  
  // Get the base API URL
  getBaseApiUrl(): string {
    return this.API_BASE_URL;
  }
  
  // Environment-specific configuration
  getConfig() {
    return {
      apiUrl: this.API_BASE_URL,
      environment: 'development',
      version: '1.0.0'
    };
  }
}
