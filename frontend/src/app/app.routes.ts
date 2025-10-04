import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    loadComponent: () => import('./components/home/home.component').then(m => m.HomeComponent)
  },
  {
    path: 'upload',
    loadComponent: () => import('./components/file-upload/file-upload.component').then(m => m.FileUploadComponent)
  }
];
