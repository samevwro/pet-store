package pet.store.controller.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pet.store.entity.Customer;
import pet.store.entity.Employee;
import pet.store.entity.PetStore;

@NoArgsConstructor
@Data
public class PetStoreData {
	private Long petStoreId;

	private String petStoreName;
	private String petStoreAddress;
	private String petStoreCity;
	private String petStoreState;
	private Long petStoreZip;
	private Long petStorePhone;

	Set<PetStoreCustomer> customers = new HashSet<>();
	Set<PetStoreEmployee> employees = new HashSet<>();
	
	
	public PetStoreData PetStore(PetStore petStore) {
		petStoreId = petStore.getPetStoreId();
		petStoreName = petStore.getPetStoreName();
		petStoreAddress = petStore.getPetStoreAddress();
		petStoreCity = petStore.getPetStoreCity();
		petStoreState = petStore.getPetStoreState();
		petStoreZip = petStore.getPetStoreZip();
		petStorePhone = petStore.getPetStorePhone();
		
		for(PetStoreEmployee employee : petStore.getEmployees()) {
			this.employees.add(employee);
		}
	}
	
public static class PetStoreCustomer{
	
	private Long customerId;
	private String customerFirstName;
	private String customerLastName;
	private String customerEmail;
	
}

public static class PetStoreEmployee{
private Long employeeId;
	
	private String employeeFirstName;
	private String employeeLastName;
	private Long employeePhone;
	private String employeeJobTitle;
	
}
}
