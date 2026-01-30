package pet.store.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;
import pet.store.controller.model.PetStoreData;
import pet.store.controller.model.PetStoreData.PetStoreCustomer;
import pet.store.controller.model.PetStoreData.PetStoreEmployee;
import pet.store.service.PetStoreService;

@RestController
@RequestMapping("/pet_store")
@Slf4j
public class PetStoreController {
	@Autowired
	private PetStoreService petStoreService;
	
	@PostMapping("/pet_store_post")
	@ResponseStatus(HttpStatus.CREATED)
	public PetStoreData insertPetStoreData(@RequestBody PetStoreData data) {
		log.info("Creating pet store {}", data);
		return petStoreService.savePetStore(data);
	}
	
	@PostMapping("/pet_store/{petStoreId}/employee")
	@ResponseStatus(HttpStatus.CREATED)
	public PetStoreEmployee insertPetStoreEmployee(@RequestBody PetStoreEmployee petStoreEmployee, @PathVariable Long petStoreId) {
		log.info("Creating pet store employee {}", petStoreEmployee);
		return petStoreService.saveEmployee(petStoreEmployee, petStoreId);
	}
	
	@PostMapping("/pet_store/{petStoreId}/customer")
	@ResponseStatus(HttpStatus.CREATED)
	public PetStoreCustomer insertPetStoreCustomer(@RequestBody PetStoreCustomer petStoreCustomer, @PathVariable Long petStoreId) {
		log.info("Creating pet store customer {}", petStoreCustomer);
		return petStoreService.saveCustomer(petStoreCustomer, petStoreId);
	}
	
	@PutMapping("/pet_store_put/{petStoreId}")
	public PetStoreData updatePetStoreData(@RequestBody PetStoreData data,@PathVariable Long petStoreId) {
		data.setPetStoreId(petStoreId);
		log.info("Updating pet store {}", petStoreId);
		return petStoreService.savePetStore(data);
	}
	
	@GetMapping
	public List<PetStoreData> getPetStoreList(){
		log.info("Retrieving all pet stores.");
		return petStoreService.retrieveAllPetStores();
	}
	
	@GetMapping("/pet_store/{petStoreId}")
	public PetStoreData getPetStore(@PathVariable Long petStoreId) {
		log.info("Retrieving pet store with ID={}", petStoreId);
		return petStoreService.retrievePetStore(petStoreId);
	}
	
	@DeleteMapping("/pet_store/{petStoreId}")
	public Map<String, String> deletePetStore(@PathVariable Long petStoreId) {
		log.info("Deleting pet store with ID={}", petStoreId);
		return petStoreService.deletePetStoreById(petStoreId);
	}
	
	
}
