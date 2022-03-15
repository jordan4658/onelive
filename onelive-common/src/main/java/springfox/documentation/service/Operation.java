//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package springfox.documentation.service;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Maps.EntryTransformer;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.http.HttpMethod;
import springfox.documentation.schema.ModelReference;

public class Operation {
    private final HttpMethod method;
    private final String summary;
    private final String notes;
    private final ModelReference responseModel;
    private final String uniqueId;
    private final int position;
    private final Set<String> tags;
    private final Set<String> produces;
    private final Set<String> consumes;
    private final Set<String> protocol;
    private final boolean isHidden;
    private final Map<String, List<AuthorizationScope>> securityReferences;
    private final List<Parameter> parameters;
    private final Set<ResponseMessage> responseMessages;
    private final String deprecated;
    private final List<VendorExtension> vendorExtensions;

    public Operation(HttpMethod method, String summary, String notes, ModelReference responseModel, String uniqueId, int position, Set<String> tags, Set<String> produces, Set<String> consumes, Set<String> protocol, List<SecurityReference> securityReferences, List<Parameter> parameters, Set<ResponseMessage> responseMessages, String deprecated, boolean isHidden, Collection<VendorExtension> vendorExtensions) {
        this.method = method;
        this.summary = summary;
        this.notes = notes;
        this.responseModel = responseModel;
        this.uniqueId = uniqueId;
        this.position = position;
        this.tags = tags;
        this.produces = produces;
        this.consumes = consumes;
        this.protocol = protocol;
        this.isHidden = isHidden;
        this.securityReferences = this.toAuthorizationsMap(securityReferences);
        this.parameters = FluentIterable.from(parameters).toSortedList(this.byParameterName());
        this.responseMessages = responseMessages;
        this.deprecated = deprecated;
        this.vendorExtensions = Lists.newArrayList(vendorExtensions);
    }

    public boolean isHidden() {
        return this.isHidden;
    }

    public ModelReference getResponseModel() {
        return this.responseModel;
    }

    public Set<String> getTags() {
        return this.tags;
    }

    private Map<String, List<AuthorizationScope>> toAuthorizationsMap(List<SecurityReference> securityReferences) {
        return Maps.transformEntries(Maps.uniqueIndex(securityReferences, this.byType()), this.toScopes());
    }

    private EntryTransformer<? super String, ? super SecurityReference, List<AuthorizationScope>> toScopes() {
        return new EntryTransformer<String, SecurityReference, List<AuthorizationScope>>() {
            public List<AuthorizationScope> transformEntry(String key, SecurityReference value) {
                return Lists.newArrayList(value.getScopes());
            }
        };
    }

    private Function<? super SecurityReference, String> byType() {
        return new Function<SecurityReference, String>() {
            public String apply(SecurityReference input) {
                return input.getReference();
            }
        };
    }

    public HttpMethod getMethod() {
        return this.method;
    }

    public String getSummary() {
        return this.summary;
    }

    public String getNotes() {
        return this.notes;
    }

    public String getUniqueId() {
        return this.uniqueId;
    }

    public int getPosition() {
        return this.position;
    }

    public Set<String> getProduces() {
        return this.produces;
    }

    public Set<String> getConsumes() {
        return this.consumes;
    }

    public Set<String> getProtocol() {
        return this.protocol;
    }

    public Map<String, List<AuthorizationScope>> getSecurityReferences() {
        return this.securityReferences;
    }

    public List<Parameter> getParameters() {
        return this.parameters;
    }

    public Set<ResponseMessage> getResponseMessages() {
        return this.responseMessages;
    }

    public String getDeprecated() {
        return this.deprecated;
    }

    public List<VendorExtension> getVendorExtensions() {
        return this.vendorExtensions;
    }

    private Comparator<Parameter> byParameterName() {
        return new Comparator<Parameter>() {
            public int compare(Parameter first, Parameter second) {
                return first.getParamType().compareTo(second.getParamType());
            }
        };
    }
}
