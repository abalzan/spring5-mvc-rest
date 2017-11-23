package br.com.andrei.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import br.com.andrei.domain.Category;
import br.com.andrei.domain.Customer;
import br.com.andrei.domain.Vendor;
import br.com.andrei.repository.CategoryRepository;
import br.com.andrei.repository.CustomerRepository;
import br.com.andrei.repository.VendorRepository;

@Component
public class BootStrap implements CommandLineRunner {

	/*
	 * CommandLineRunner is an specific spring boot class, that 
	 * enable us to load some data on the start of Spring application 
	 */
	private CategoryRepository categoryRepository;
	private CustomerRepository customerRepository;
	private VendorRepository vendorRepository;



	public BootStrap(CategoryRepository categoryRepository, CustomerRepository customerRepository,
			VendorRepository vendorRepository) {
		this.categoryRepository = categoryRepository;
		this.customerRepository = customerRepository;
		this.vendorRepository = vendorRepository;
	}

	@Override
	public void run(String... args) throws Exception {
	
		loadCategories();
		loadCustomers();
		loadVendors();
	
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
	
	private void loadVendors() {
		Vendor vendor1 = new Vendor();
		vendor1.setName("Samsung");
		
		Vendor vendor2 = new Vendor();
		vendor2.setName("Apple");
		
		Vendor vendor3 = new Vendor();
		vendor3.setName("Motorola");
		
		Vendor vendor4 = new Vendor();
		vendor4.setName("Lenovo");
		
		Vendor vendor5 = new Vendor();
		vendor5.setName("Intel");
		
		vendorRepository.save(vendor1);
		vendorRepository.save(vendor2);
		vendorRepository.save(vendor3);
		vendorRepository.save(vendor4);
		vendorRepository.save(vendor5);
		
		System.out.println("We have "+vendorRepository.count() +" vendors");
	}
	
	
	
}


