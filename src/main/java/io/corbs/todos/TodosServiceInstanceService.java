package io.corbs.todos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.servicebroker.exception.ServiceInstanceDoesNotExistException;
import org.springframework.cloud.servicebroker.model.instance.*;
import org.springframework.cloud.servicebroker.model.instance.CreateServiceInstanceResponse.CreateServiceInstanceResponseBuilder;
import org.springframework.cloud.servicebroker.service.ServiceInstanceService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TodosServiceInstanceService implements ServiceInstanceService {

    private static final Logger LOG = LoggerFactory.getLogger(TodosServiceInstanceService.class);

    private final TodosServiceInstanceRepo instanceRepo;

    @Autowired
    public TodosServiceInstanceService(TodosServiceInstanceRepo instanceRepo) {
        this.instanceRepo = instanceRepo;
    }

    @Override
    public CreateServiceInstanceResponse createServiceInstance(CreateServiceInstanceRequest instanceRequest) {
        // Create Frontend TodoUI and Backend TodoAPI as a Service Instance
        // Each deployment can be sized differently
        // Plan 1 - 25 Todos in memory
        // Plan 2 - 25 Todos with Persistence
        // Plan 3 - 25 Todos with Integrations
        String serviceDefinitionId = instanceRequest.getServiceDefinitionId();
        String planId = instanceRequest.getPlanId();
        String instanceId = instanceRequest.getServiceInstanceId();

        LOG.info("creating service-instance serviceDefinitionId="
            + serviceDefinitionId + ",serviceInstanceId="
            + instanceId + ",planId=" + planId);

        CreateServiceInstanceResponseBuilder responseBuilder = CreateServiceInstanceResponse.builder();

        if(instanceRepo.findById(instanceId).isPresent()) {
            responseBuilder.instanceExisted(true);
        } else {
            instanceRepo.save(TodosServiceInstance.builder()
                .instanceId(instanceId)
                .planId(planId)
                .serviceDefinitionId(serviceDefinitionId).build());
            // actually create the service :)
        }

        return responseBuilder.build();
    }

    @Override
    public GetServiceInstanceResponse getServiceInstance(GetServiceInstanceRequest request) {
        String instanceId = request.getServiceInstanceId();
        Optional<TodosServiceInstance> serviceInstance = instanceRepo.findById(instanceId);
        if(serviceInstance.isPresent()) {
            TodosServiceInstance instance = serviceInstance.get();
            return GetServiceInstanceResponse.builder()
                .serviceDefinitionId(instance.getServiceDefinitionId())
                .planId(instance.getPlanId())
                .parameters(instance.getParameters())
                .build();
        } else {
            throw new ServiceInstanceDoesNotExistException(instanceId);
        }
    }

    @Override
    public DeleteServiceInstanceResponse deleteServiceInstance(DeleteServiceInstanceRequest request) {
        String instanceId = request.getServiceInstanceId();
        if(instanceRepo.findById(instanceId).isPresent()) {
            instanceRepo.deleteById(instanceId);
            return DeleteServiceInstanceResponse.builder().build();
        } else {
            throw new ServiceInstanceDoesNotExistException(instanceId);
        }
    }

}
