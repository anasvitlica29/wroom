package wroom.authservice.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@NamedEntityGraph(name = "User.Roles.Permissions", attributeNodes = @NamedAttributeNode(value = "roles", subgraph = "permissions"), subgraphs = @NamedSubgraph(name = "permissions", attributeNodes = @NamedAttributeNode("permissions")))
@Inheritance(strategy = InheritanceType.JOINED)
@Setter @Getter @NoArgsConstructor @AllArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String surname;

	@Column(nullable = false, unique = true)
	private String email;

	@Column
	private String password;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;

	@Column(nullable = false)
	private boolean enabled;

	@Column(nullable = false)
	private boolean nonLocked;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="last_password_change")
	private Date lastPasswordChange;
	
	@Column
	private String businessNumber;
	
	@Column
	private String address;
	
}
