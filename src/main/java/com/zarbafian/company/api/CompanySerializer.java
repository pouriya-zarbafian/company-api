package com.zarbafian.company.api;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.zarbafian.company.model.Company;
import com.zarbafian.company.model.Owner;

import java.io.IOException;

public class CompanySerializer extends StdSerializer<Company> {

    public CompanySerializer() {
        super(Company.class);
    }

    public CompanySerializer(Class<Company> t) {
        super(t);
    }

    @Override
    public void serialize(Company company, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", company.getId());
        jsonGenerator.writeStringField("name", company.getName());
        jsonGenerator.writeStringField("address1", company.getAddress());
        jsonGenerator.writeStringField("city", company.getCity());
        jsonGenerator.writeObjectField("country", company.getCountry());
        jsonGenerator.writeStringField("email", company.getEmail());
        jsonGenerator.writeStringField("phone", company.getPhoneNumber());

        if(company.getOwners() != null) {
            jsonGenerator.writeArrayFieldStart("owners");
            for (Owner owner : company.getOwners()) {
                jsonGenerator.writeObject(owner);
            }
            jsonGenerator.writeEndArray();
        }

        jsonGenerator.writeEndObject();
    }
}
