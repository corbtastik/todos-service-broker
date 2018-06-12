package io.corbs.todos;

import lombok.*;

import javax.persistence.*;
import java.util.Map;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
@Entity
@Table(name="service_instances")
class TodosServiceInstance {
    @Id
    @Column(length = 64)
    private String instanceId;

    @Column(length = 64)
    private String serviceDefinitionId;

    @Column(length = 64)
    private String planId;

    @ElementCollection
    @MapKeyColumn(name="parameter_name", length = 100)
    @Column(name = "parameter_value")
    @CollectionTable(name="service_instance_parameters", joinColumns = @JoinColumn(name = "instance_id"))
    @Convert(converter = ObjectToStringConverter.class, attributeName = "value")
    private Map<String, Object> parameters;
}
