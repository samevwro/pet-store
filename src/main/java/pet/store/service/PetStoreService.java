package pet.store.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pet.store.controller.model.PetStoreData;
import pet.store.controller.model.PetStoreData.PetStoreCustomer;
import pet.store.controller.model.PetStoreData.PetStoreEmployee;
import pet.store.dao.CustomerDao;
import pet.store.dao.EmployeeDao;
import pet.store.dao.PetStoreDao;
import pet.store.entity.Customer;
import pet.store.entity.Employee;
import pet.store.entity.PetStore;

@Service
public class PetStoreService {
	@Autowired
	private PetStoreDao petStoreDao;

	@Autowired
	private EmployeeDao employeeDao;
	
	@Autowired 
	private CustomerDao customerDao;

	@Transactional(readOnly = false)
	public PetStoreData savePetStore(PetStoreData data) {
		Long petStoreId = data.getPetStoreId();
		PetStore petStore = findOrCreatePetStore(petStoreId);

		copyPetStoreFields(petStore, data);

		PetStore savedPetStore = petStoreDao.save(petStore);
		return new PetStoreData(savedPetStore);
	}

	private void copyPetStoreFields(PetStore petStore, PetStoreData data) {
		petStore.setPetStoreName(data.getPetStoreName());
		petStore.setPetStoreAddress(data.getPetStoreAddress());
		petStore.setPetStoreCity(data.getPetStoreCity());
		petStore.setPetStoreState(data.getPetStoreState());
		petStore.setPetStoreZip(data.getPetStoreZip());
		petStore.setPetStorePhone(data.getPetStorePhone());
	}

	private PetStore findOrCreatePetStore(Long petStoreId) {
		if (Objects.isNull(petStoreId)) {
			return new PetStore();
		} else {
			return findPetStoreById(petStoreId);
		}
	}

	private PetStore findPetStoreById(Long petStoreId) {
		return petStoreDao.findById(petStoreId)
				.orElseThrow(() -> new NoSuchElementException("Pet store with ID=" + petStoreId + " not found"));
	}

	@Transactional(readOnly = false)
	public PetStoreEmployee saveEmployee(PetStoreEmployee petStoreEmployee, Long petStoreId) {
		PetStore petStore = findPetStoreById(petStoreId);
		Long employeeId = petStoreEmployee.getEmployeeId();
		Employee employee = findOrCreateEmployee(petStoreId, employeeId);

		copyEmployeeFields(employee, petStoreEmployee);
		
		employee.setPetStore(petStore);
		
		petStore.getEmployees().add(employee);
		Employee savedEmployee = employeeDao.save(employee);
		return new PetStoreEmployee(savedEmployee);

	}

	private void copyEmployeeFields(Employee employee, PetStoreEmployee petStoreEmployee) {
		employee.setEmployeeFirstName(petStoreEmployee.getEmployeeFirstName());
		employee.setEmployeeLastName(petStoreEmployee.getEmployeeLastName());
		employee.setEmployeePhone(petStoreEmployee.getEmployeePhone());
		employee.setEmployeeJobTitle(petStoreEmployee.getEmployeeJobTitle());
	}

	private Employee findOrCreateEmployee(Long petStoreId, Long employeeId) {
		if(Objects.isNull(petStoreId)) {
			new NoSuchElementException("Pet store with ID=" + petStoreId + " not found");
			return null;
		}else {
		if (Objects.isNull(employeeId)) {
			return new Employee();
		} else {
			return findEmployeeById(employeeId);
		}}
	}

	private Employee findEmployeeById(Long employeeId) {
		return employeeDao.findById(employeeId)
				.orElseThrow(() -> new NoSuchElementException("Employee with ID=" + employeeId + " not found"));
	}

	public PetStoreCustomer saveCustomer(PetStoreCustomer petStoreCustomer, Long petStoreId) {
		PetStore petStore = findPetStoreById(petStoreId);
		Long customerId = petStoreCustomer.getCustomerId();
		Customer customer = findOrCreateCustomer(customerId, petStoreId);
		
		copyCustomerFields(customer, petStoreCustomer);
		
		petStore.getCustomers().add(customer);
		Customer savedCustomer = customerDao.save(customer);
		return new PetStoreCustomer(savedCustomer);
	}

	private void copyCustomerFields(Customer customer, PetStoreCustomer petStoreCustomer) {
		customer.setCustomerFirstName(petStoreCustomer.getCustomerFirstName());
		customer.setCustomerLastName(petStoreCustomer.getCustomerLastName());
		customer.setCustomerEmail(petStoreCustomer.getCustomerEmail());
	}

	private Customer findOrCreateCustomer(Long customerId, Long petStoreId) {
//		Customer customer = findCustomerById(customerId);
//		for(PetStore petStore : customer.getPetStore) {
//			
//		}
		
		if(Objects.isNull(petStoreId)) {
			new NoSuchElementException("Pet store with ID=" + petStoreId + " not found");
			return null;
		}else {
		if (Objects.isNull(customerId)) {
			return new Customer();
		} else {
			return findCustomerById(customerId);
		}}
	}

	private Customer findCustomerById(Long customerId) {
		return customerDao.findById(customerId).orElseThrow(() -> 
		new NoSuchElementException("Customer with Id=" + customerId + " not found"));
	}
	
	@Transactional(readOnly = true)
	public List<PetStoreData> retrieveAllPetStores() {
		List<PetStore> petStores = petStoreDao.findAll();
		List<PetStoreData> result = new LinkedList<>();
		
		for(PetStore petStore : petStores) {
			PetStoreData psd = new PetStoreData(petStore);
			
			psd.getCustomers().clear();
			psd.getEmployees().clear();
			
			result.add(psd);
		}
		return result;
	}
	@Transactional(readOnly = true)
	public PetStoreData retrievePetStore(Long petStoreId) {
		PetStoreData result = new PetStoreData(findPetStoreById(petStoreId));
		return result;
	}

	public Map<String, String> deletePetStoreById(Long petStoreId) {
		PetStore petStore = findPetStoreById(petStoreId);
		petStoreDao.delete(petStore);
		
		return Map.of(
				"message", "Pet store with ID=" + petStoreId + " was deleted successfully");
	}
	}
