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

        if(company.getId() != null){
            jsonGenerator.writeNumberField("id", company.getId());
        }
        if(company.getName() != null){
            jsonGenerator.writeStringField("name", company.getName());
        }
        if(company.getAddress() != null){
            jsonGenerator.writeStringField("address", company.getAddress());
        }
        if(company.getCity() != null){
            jsonGenerator.writeStringField("city", company.getCity());
        }
        if(company.getCountry() != null){
            jsonGenerator.writeObjectField("country", company.getCountry());
        }
        if(company.getEmail() != null){
            jsonGenerator.writeStringField("email", company.getEmail());
        }
        if(company.getPhoneNumber() != null){
            jsonGenerator.writeStringField("phone", company.getPhoneNumber());
        }

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
