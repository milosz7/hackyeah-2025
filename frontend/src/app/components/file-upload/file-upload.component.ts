import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { FileUploadModule } from 'primeng/fileupload';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { MessageModule } from 'primeng/message';
import { ProgressBarModule } from 'primeng/progressbar';
import { TagModule } from 'primeng/tag';
import { HttpClient, HttpEventType, HttpRequest } from '@angular/common/http';
import { environment } from '../../config/app.config';

interface UploadResponse {
  id: number;
  fileName: string;
  originalFileName: string;
  fileSize: number;
  contentType: string;
  validationStatus: string;
  reportingPeriod: string;
  entityName: string;
  submittedBy: string;
  submittedAt: string;
}

@Component({
  selector: 'app-file-upload',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    FileUploadModule,
    ButtonModule,
    CardModule,
    MessageModule,
    ProgressBarModule,
    TagModule
  ],
  template: `
    <div class="max-w-4xl mx-auto p-6">
      <p-card header="Report File Upload" class="mb-6">
        <div class="grid grid-cols-1 lg:grid-cols-2 gap-6">
          <!-- Upload Form -->
          <div>
            <h3 class="text-lg font-semibold text-gray-800 mb-4">Upload XLSX Report</h3>
            
            <form (ngSubmit)="onSubmit()" #uploadForm="ngForm" class="space-y-4">
              <!-- File Upload -->
              <div class="mb-4">
                <label class="block text-sm font-medium text-gray-700 mb-2">
                  Select Report File (XLSX only)
                </label>
                <p-fileUpload 
                  #fileUpload
                  mode="basic" 
                  name="file"
                  accept=".xlsx,.xls"
                  maxFileSize="10000000"
                  (onSelect)="onFileSelect($event)"
                  (onError)="onUploadError($event)"
                  chooseLabel="Choose File"
                  cancelLabel="Cancel"
                  class="w-full">
                </p-fileUpload>
                <p-message 
                  *ngIf="errorMessage" 
                  severity="error" 
                  [text]="errorMessage" 
                  class="mt-2">
                </p-message>
              </div>

              <!-- Form Fields -->
              <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                <div>
                  <label class="block text-sm font-medium text-gray-700 mb-2">
                    Supervised Entity ID
                  </label>
                  <input 
                    type="number" 
                    [(ngModel)]="formData.supervisedEntityId" 
                    name="supervisedEntityId"
                    class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                    placeholder="Enter Entity ID"
                    required>
                </div>
                
                <div>
                  <label class="block text-sm font-medium text-gray-700 mb-2">
                    Submitted By User ID
                  </label>
                  <input 
                    type="number" 
                    [(ngModel)]="formData.submittedByUserId" 
                    name="submittedByUserId"
                    class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                    placeholder="Enter User ID"
                    required>
                </div>
              </div>

              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">
                  Reporting Period
                </label>
                <input 
                  type="text" 
                  [(ngModel)]="formData.reportingPeriod" 
                  name="reportingPeriod"
                  class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                  placeholder="e.g., Q1_2025"
                  required>
              </div>

              <!-- Upload Button -->
              <div class="flex justify-end">
                <p-button 
                  type="submit"
                  label="Upload Report"
                  icon="pi pi-upload"
                  [loading]="isUploading"
                  [disabled]="!selectedFile || isUploading"
                  severity="success">
                </p-button>
              </div>
            </form>
          </div>

          <!-- Upload Status -->
          <div>
            <h3 class="text-lg font-semibold text-gray-800 mb-4">Upload Status</h3>
            
            <!-- Progress Bar -->
            <div *ngIf="isUploading" class="mb-4">
              <p-progressBar 
                [value]="uploadProgress" 
                [showValue]="true"
                class="mb-2">
              </p-progressBar>
              <p class="text-sm text-gray-600">Uploading file...</p>
            </div>

            <!-- Success Message -->
            <div *ngIf="uploadSuccess" class="mb-4">
              <p-message 
                severity="success" 
                text="File uploaded successfully!" 
                class="mb-2">
              </p-message>
              <div class="bg-green-50 p-4 rounded-lg">
                <h4 class="font-semibold text-green-800 mb-2">Upload Details:</h4>
                <div class="text-sm text-green-700 space-y-1">
                  <p><strong>File:</strong> {{ uploadResponse?.originalFileName }}</p>
                  <p><strong>Size:</strong> {{ (uploadResponse?.fileSize || 0) / 1024 | number:'1.0-0' }} KB</p>
                  <p><strong>Status:</strong> 
                    <p-tag 
                      [value]="uploadResponse?.validationStatus" 
                      [severity]="getStatusSeverity(uploadResponse?.validationStatus)">
                    </p-tag>
                  </p>
                  <p><strong>Entity:</strong> {{ uploadResponse?.entityName }}</p>
                  <p><strong>Period:</strong> {{ uploadResponse?.reportingPeriod }}</p>
                </div>
              </div>
            </div>

            <!-- File Info -->
            <div *ngIf="selectedFile && !uploadSuccess" class="bg-blue-50 p-4 rounded-lg">
              <h4 class="font-semibold text-blue-800 mb-2">Selected File:</h4>
              <div class="text-sm text-blue-700 space-y-1">
                <p><strong>Name:</strong> {{ selectedFile.name }}</p>
                <p><strong>Size:</strong> {{ selectedFile.size / 1024 | number:'1.0-0' }} KB</p>
                <p><strong>Type:</strong> {{ selectedFile.type }}</p>
              </div>
            </div>
          </div>
        </div>
      </p-card>
    </div>
  `,
  styles: []
})
export class FileUploadComponent {
  selectedFile: File | null = null;
  isUploading = false;
  uploadProgress = 0;
  uploadSuccess = false;
  errorMessage = '';
  uploadResponse: UploadResponse | null = null;

  formData = {
    supervisedEntityId: 1,
    submittedByUserId: 1,
    reportingPeriod: 'Q1_2025'
  };

  constructor(private http: HttpClient) {}

  onFileSelect(event: any) {
    this.selectedFile = event.files[0];
    this.errorMessage = '';
    this.uploadSuccess = false;
    this.uploadResponse = null;
  }

  onUploadError(event: any) {
    this.errorMessage = 'File upload error: ' + event.error;
  }

  onSubmit() {
    if (!this.selectedFile) {
      this.errorMessage = 'Please select a file to upload';
      return;
    }

    this.isUploading = true;
    this.uploadProgress = 0;
    this.errorMessage = '';
    this.uploadSuccess = false;

    const formData = new FormData();
    formData.append('file', this.selectedFile);
    formData.append('supervisedEntityId', this.formData.supervisedEntityId.toString());
    formData.append('submittedByUserId', this.formData.submittedByUserId.toString());
    formData.append('reportingPeriod', this.formData.reportingPeriod);

    const req = new HttpRequest('POST', `${environment.apiUrl}/api/reports/upload`, formData, {
      reportProgress: true
    });

    this.http.request(req).subscribe({
      next: (event: any) => {
        if (event.type === HttpEventType.UploadProgress) {
          this.uploadProgress = Math.round(100 * event.loaded / event.total);
        } else if (event.type === HttpEventType.Response) {
          this.uploadSuccess = true;
          this.uploadResponse = event.body;
          this.isUploading = false;
        }
      },
      error: (error) => {
        this.errorMessage = 'Upload failed: ' + (error.error?.message || error.message);
        this.isUploading = false;
        this.uploadProgress = 0;
      }
    });
  }

  getStatusSeverity(status: string | undefined): 'success' | 'secondary' | 'info' | 'warn' | 'danger' | 'contrast' | null {
    if (!status) return 'info';
    
    switch (status) {
      case 'SUCCESSFUL': return 'success';
      case 'ERRORS': return 'warn';
      case 'TECHNICAL_ERROR': return 'danger';
      case 'WORKING': return 'info';
      case 'TRANSMITTED': return 'info';
      case 'ONGOING': return 'info';
      default: return 'info';
    }
  }
}
