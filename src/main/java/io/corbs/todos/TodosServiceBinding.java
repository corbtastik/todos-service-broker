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
@Table(name="service_bindings")
class TodosServiceBinding {
    @Id
    @Column(length = 64)
    private String bindingId;

    @ElementCollection
    @MapKeyColumn(name = "parameter_name", length = 100)
    @Column(name = "parameter_value")
    @CollectionTable(name = "service_binding_parameters", joinColumns = @JoinColumn(name = "binding_id"))
    @Convert(converter = ObjectToStringConverter.class, attributeName = "value")
    private Map<String, Object> parameters;

    @ElementCollection
    @MapKeyColumn(name = "credential_name", length = 100)
    @Column(name = "credential_value")
    @CollectionTable(name = "service_binding_credentials", joinColumns = @JoinColumn(name = "binding_id"))
    @Convert(converter = ObjectToStringConverter.class, attributeName = "value")
    private Map<String, Object> credentials;
}
