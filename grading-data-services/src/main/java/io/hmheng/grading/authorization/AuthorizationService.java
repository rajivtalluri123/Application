package io.hmheng.grading.authorization;

public interface AuthorizationService {

    enum Service {
        SCORING,
        GRADING
    }
    
    AuthorizationDetails createSIFAuthorization(Service service);

}


