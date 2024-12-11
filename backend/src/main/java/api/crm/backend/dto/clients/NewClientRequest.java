package api.crm.backend.dto.clients;

import jakarta.validation.constraints.NotBlank;

public class NewClientRequest {
    @NotBlank(message = "El nombre no puede estar vacío")
    private String name;

    @NotBlank(message = "El email no puede estar vacío")
    private String email;

    @NotBlank(message = "La empresa no puede estar vacía")
    private String company;

    @NotBlank(message = "El teléfono no puede estar vacío")
    private String phone;

    public NewClientRequest(String name, String email, String company, String phone) {
        this.name = name;
        this.email = email;
        this.company = company;
        this.phone = phone;
    }

    public NewClientRequest() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
