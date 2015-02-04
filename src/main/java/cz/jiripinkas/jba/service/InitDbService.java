package cz.jiripinkas.jba.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import cz.jiripinkas.jba.entity.Blog;
import cz.jiripinkas.jba.entity.Role;
import cz.jiripinkas.jba.entity.User;
import cz.jiripinkas.jba.repository.BlogRepository;
import cz.jiripinkas.jba.repository.ItemRepository;
import cz.jiripinkas.jba.repository.RoleRepository;
import cz.jiripinkas.jba.repository.UserRepository;

@Transactional
@Service
public class InitDbService {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BlogRepository blogRepository;

	@Autowired
	private ItemRepository itemRepository;

	@PostConstruct
	public void init() {
		if (roleRepository.findByName("ROLE_ADMIN") == null) {
			Role roleUser = new Role();
			roleUser.setName("ROLE_USER");
			roleRepository.save(roleUser);

			Role roleAdmin = new Role();
			roleAdmin.setName("ROLE_ADMIN");
			roleRepository.save(roleAdmin);

			User userAdmin = new User();
			userAdmin.setEnabled(true);
			userAdmin.setAdmin(true);
			userAdmin.setName("admin");
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			userAdmin.setPassword(encoder.encode("admin"));
			List<Role> roles = new ArrayList<Role>();
			roles.add(roleAdmin);
			roles.add(roleUser);
			userAdmin.setRoles(roles);
			userRepository.save(userAdmin);

			Blog blogSpring = new Blog();
			blogSpring.setName("Spring");
			blogSpring.setUrl("http://spring.io/blog.atom");
			blogSpring.setHomepageUrl("http://spring.io/");
			blogSpring.setShortName("spring");
			blogSpring.setUser(userAdmin);
			blogRepository.save(blogSpring);

			Blog blogJavavids = new Blog();
			blogJavavids.setName("javavids");
			blogJavavids.setUrl("http://feeds.feedburner.com/javavids?format=xml");
			blogJavavids.setHomepageUrl("http://www.javavids.com");
			blogJavavids.setShortName("javavids");
			blogJavavids.setUser(userAdmin);
			blogRepository.save(blogJavavids);

			Blog blogJavaSkoleni = new Blog();
			blogJavaSkoleni.setName("java skoleni");
			blogJavaSkoleni.setUrl("http://novinky.seico.cz/java-skoleni");
			blogJavaSkoleni.setHomepageUrl("http://www.java-skoleni.cz");
			blogJavaSkoleni.setShortName("java-skoleni");
			blogJavaSkoleni.setUser(userAdmin);
			blogRepository.save(blogJavaSkoleni);

			Blog blogSqlSkoleni = new Blog();
			blogSqlSkoleni.setName("sql skoleni");
			blogSqlSkoleni.setUrl("http://novinky.seico.cz/sql-skoleni");
			blogSqlSkoleni.setHomepageUrl("http://www.sql-skoleni.cz");
			blogSqlSkoleni.setShortName("sql-skoleni");
			blogSqlSkoleni.setUser(userAdmin);
			blogRepository.save(blogSqlSkoleni);
		}

	}
}
