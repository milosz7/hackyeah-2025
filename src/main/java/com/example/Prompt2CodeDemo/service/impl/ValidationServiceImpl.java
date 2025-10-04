package com.example.Prompt2CodeDemo.service.impl;

import com.example.Prompt2CodeDemo.entities.Report;
import com.example.Prompt2CodeDemo.service.ValidationService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Service
public class ValidationServiceImpl implements ValidationService {
    
    private final Random random = new Random();
    
    @Override
    public ValidationResult validateReport(Report report) {
        // Simulate external validation tool processing time
        try {
            Thread.sleep(1000 + random.nextInt(2000)); // 1-3 seconds
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Randomly select validation status based on realistic probabilities
        Report.ValidationStatus status = getRandomValidationStatus();
        String errorMessage = null;
        String validationDetails = null;
        boolean hasErrors = false;
        
        switch (status) {
            case SUCCESSFUL:
                validationDetails = "All validation rules passed successfully. Report data is complete and accurate.";
                break;
            case ERRORS:
                hasErrors = true;
                errorMessage = "Validation errors detected in the report data";
                validationDetails = generateValidationErrors();
                break;
            case TECHNICAL_ERROR:
                hasErrors = true;
                errorMessage = "Technical error occurred during validation process";
                validationDetails = generateTechnicalError();
                break;
            case ERROR_EXCEEDED_TIME:
                hasErrors = true;
                errorMessage = "Validation process exceeded maximum processing time";
                validationDetails = "The validation process was not completed within the allowed time limit of 24 hours.";
                break;
            default:
                validationDetails = "Report is being processed by the validation system.";
        }
        
        return new ValidationResult(status, errorMessage, validationDetails, hasErrors);
    }
    
    @Override
    public String generateValidationResultFile(Report report, ValidationResult result) {
        StringBuilder content = new StringBuilder();
        
        // UKNF Header
        content.append("=== UKNF VALIDATION RESULT ===\n");
        content.append("Komisja Nadzoru Finansowego\n");
        content.append("System Walidacji Raportów\n\n");
        
        // Report Information
        content.append("INFORMACJE O RAPORCIE:\n");
        content.append("Nazwa pliku: ").append(report.getOriginalFileName()).append("\n");
        content.append("Nazwa podmiotu: ").append(report.getEntityName()).append("\n");
        content.append("Okres raportowania: ").append(report.getReportingPeriod()).append("\n");
        content.append("Data złożenia: ").append(report.getSubmittedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\n");
        content.append("Złożony przez: ").append(report.getSubmittedBy()).append("\n\n");
        
        // Validation Information
        content.append("INFORMACJE O WALIDACJI:\n");
        content.append("Data walidacji: ").append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\n");
        content.append("Status walidacji: ").append(getStatusDescription(result.getStatus())).append("\n");
        content.append("ID raportu: ").append(report.getId()).append("\n\n");
        
        // Validation Details
        content.append("SZCZEGÓŁY WALIDACJI:\n");
        content.append(result.getValidationDetails()).append("\n\n");
        
        // Error Information (if applicable)
        if (result.hasErrors() && result.getErrorMessage() != null) {
            content.append("INFORMACJE O BŁĘDACH:\n");
            content.append("Typ błędu: ").append(result.getErrorMessage()).append("\n");
            content.append("Szczegóły: ").append(result.getValidationDetails()).append("\n\n");
        }
        
        // UKNF Footer
        content.append("=== KONIEC RAPORTU WALIDACJI ===\n");
        content.append("Wygenerowano przez system UKNF\n");
        content.append("Data: ").append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\n");
        
        return content.toString();
    }
    
    private Report.ValidationStatus getRandomValidationStatus() {
        // Realistic probability distribution for validation outcomes
        int randomValue = random.nextInt(100);
        
        if (randomValue < 60) {          // 60% chance of success
            return Report.ValidationStatus.SUCCESSFUL;
        } else if (randomValue < 80) {   // 20% chance of validation errors
            return Report.ValidationStatus.ERRORS;
        } else if (randomValue < 90) {   // 10% chance of technical errors
            return Report.ValidationStatus.TECHNICAL_ERROR;
        } else if (randomValue < 95) {  // 5% chance of timeout
            return Report.ValidationStatus.ERROR_EXCEEDED_TIME;
        } else {                        // 5% chance of other statuses
            return Report.ValidationStatus.ONGOING;
        }
    }
    
    private String getStatusDescription(Report.ValidationStatus status) {
        switch (status) {
            case SUCCESSFUL:
                return "WALIDACJA ZAKOŃCZONA POMYŚLNIE";
            case ERRORS:
                return "BŁĘDY WALIDACJI";
            case TECHNICAL_ERROR:
                return "BŁĄD TECHNICZNY";
            case ERROR_EXCEEDED_TIME:
                return "PRZEKROCZENIE CZASU WALIDACJI";
            case ONGOING:
                return "WALIDACJA W TOKU";
            case TRANSMITTED:
                return "RAPORT PRZEKAZANY DO WALIDACJI";
            case WORKING:
                return "PRZYGOTOWYWANIE DO WALIDACJI";
            case CONTESTED_BY_UKNF:
                return "RAPORT ZAKWESTIONOWANY PRZEZ UKNF";
            default:
                return "NIEZNANY STATUS";
        }
    }
    
    private String generateValidationErrors() {
        String[] errors = {
            "Brakujące wymagane pola w sekcji danych finansowych",
            "Nieprawidłowy format daty w kolumnie 'Data złożenia'",
            "Wartości liczbowe poza dozwolonym zakresem",
            "Brakujące podpisy cyfrowe w sekcji autoryzacji",
            "Niezgodność sum kontrolnych w tabeli transakcji",
            "Brakujące wymagane załączniki",
            "Nieprawidłowy format identyfikatora podmiotu",
            "Wartości procentowe przekraczające 100%",
            "Brakujące dane w sekcji ryzyka operacyjnego",
            "Nieprawidłowy format numeru rachunku bankowego"
        };
        
        int errorCount = 1 + random.nextInt(3); // 1-3 errors
        StringBuilder errorDetails = new StringBuilder();
        
        for (int i = 0; i < errorCount; i++) {
            if (i > 0) errorDetails.append("\n");
            errorDetails.append("• ").append(errors[random.nextInt(errors.length)]);
        }
        
        return errorDetails.toString();
    }
    
    private String generateTechnicalError() {
        String[] technicalErrors = {
            "Błąd połączenia z zewnętrznym systemem walidacji",
            "Timeout podczas przetwarzania dużego pliku",
            "Błąd parsowania struktury pliku Excel",
            "Niedostępność serwera walidacji",
            "Błąd konwersji danych do formatu wewnętrznego",
            "Przekroczenie limitu pamięci podczas przetwarzania",
            "Błąd dostępu do bazy danych walidacji",
            "Nieprawidłowy format pliku - uszkodzenie struktury"
        };
        
        return technicalErrors[random.nextInt(technicalErrors.length)];
    }
}
