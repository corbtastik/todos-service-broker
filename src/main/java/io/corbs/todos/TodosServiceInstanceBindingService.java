package io.corbs.todos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.servicebroker.exception.ServiceInstanceBindingDoesNotExistException;
import org.springframework.cloud.servicebroker.model.binding.CreateServiceInstanceAppBindingResponse;
import org.springframework.cloud.servicebroker.model.binding.CreateServiceInstanceBindingRequest;
import org.springframework.cloud.servicebroker.model.binding.CreateServiceInstanceBindingResponse;
import org.springframework.cloud.servicebroker.model.binding.DeleteServiceInstanceBindingRequest;
import org.springframework.cloud.servicebroker.service.ServiceInstanceBindingService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TodosServiceInstanceBindingService implements ServiceInstanceBindingService {

    private static final Logger LOG = LoggerFactory.getLogger(TodosServiceInstanceBindingService.class);

    private TodosServiceBindingRepo bindingRepo;

    @Autowired
    public TodosServiceInstanceBindingService(TodosServiceBindingRepo bindingRepo) {
        this.bindingRepo = bindingRepo;
    }

    @Override
    public CreateServiceInstanceBindingResponse createServiceInstanceBinding(
        CreateServiceInstanceBindingRequest bindingRequest) {

        CreateServiceInstanceAppBindingResponse.CreateServiceInstanceAppBindingResponseBuilder responseBuilder =
                CreateServiceInstanceAppBindingResponse.builder();

        Optional<TodosServiceBinding> binding = bindingRepo.findById(bindingRequest.getBindingId());
        if (binding.isPresent()) {
            responseBuilder.bindingExisted(true).credentials(binding.get().getCredentials());
        } else {
            // actually create the User
            // associate User with serviceInstanceId
            // build credentials for User and serviceInstance
            // save Binding w/ credentials
            responseBuilder.bindingExisted(false);
        }

        return responseBuilder.build();

    }

    @Override
    public void deleteServiceInstanceBinding(DeleteServiceInstanceBindingRequest bindingRequest) {
        String bindingId = bindingRequest.getBindingId();
        if (bindingRepo.existsById(bindingId)) {
            bindingRepo.deleteById(bindingId);
        } else {
            throw new ServiceInstanceBindingDoesNotExistException(bindingId);
        }
    }

}
