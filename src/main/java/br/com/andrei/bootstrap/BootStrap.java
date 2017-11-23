package br.com.andrei.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import br.com.andrei.domain.Category;
import br.com.andrei.domain.Customer;
import br.com.andrei.repository.CategoryRepository;
import br.com.andrei.repository.CustomerRepository;

@Component
public class BootStrap implements CommandLineRunner {

	/*
	 * CommandLineRunner is an specific spring boot class, that 
	 * enable us to load some data on the start of Spring application 
	 */
	private CategoryRepository categoryRepository;
	private CustomerRepository customerRepository;

	public BootStrap(CategoryRepository categoryRepository, CustomerRepository customerRepository) {
		this.categoryRepository = categoryRepository;
		this.customerRepository = customerRepository;
	}

	@Override
	public void run(String... args) throws Exception {
	
		loadCategories();
		loadCustomers();
	
	}

	private void loadCustomers() {
		       
		Customer customer1 = new Customer();
		customer1.setId(1L);
		customer1.setFirstname("John");
		customer1.setLastname("Doe");
		customerRepository.save(customer1);
		
		Customer customer2 = new Customer();
		customer2.setId(2L);
		customer2.setFirstname("Joana");
		customer2.setLastname("Does");
		customerRepository.save(customer2);
		
		Customer customer3 = new Customer();
		customer3.setId(3L);
		customer3.setFirstname("Pablo");
		customer3.setLastname("Ernesto");
		customerRepository.save(customer3);
		
		Customer customer4 = new Customer();
		customer4.setId(4L);
		customer4.setFirstname("Juan");
		customer4.setLastname("Smith");
		customerRepository.save(customer4);
		
		Customer customer5 = new Customer();
		customer5.setId(5L);
		customer5.setFirstname("Willian");
		customer5.setLastname("Test");
		customerRepository.save(customer5);
		
		System.out.println("We have "+customerRepository.count()+" customers");
	}
	
	private void loadCategories() {
		Category fruits = new Category();
		fruits.setName("Fruits");
		
		Category dried = new Category();
		dried.setName("Dried");
		
		Category fresh = new Category();
		fresh.setName("Fresh");
		
		Category exotic = new Category();
		exotic.setName("Exotic");
		
		Category nuts = new Category();
		nuts.setName("Nuts");
		
		categoryRepository.save(fruits);
		categoryRepository.save(dried);
		categoryRepository.save(fresh);
		categoryRepository.save(exotic);
		categoryRepository.save(nuts);
		
		System.out.println("We have "+categoryRepository.count() +" categories");
	}
	
	
	
	
}


