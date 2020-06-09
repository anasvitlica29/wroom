package xwsagent.wroomagent.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import xwsagent.wroomagent.converter.UserConverter;
import xwsagent.wroomagent.domain.Comment;
import xwsagent.wroomagent.domain.RentRequest;
import xwsagent.wroomagent.domain.auth.RoleName;
import xwsagent.wroomagent.domain.auth.User;
import xwsagent.wroomagent.domain.auth.VerificationToken;
import xwsagent.wroomagent.domain.dto.LoggedUserDTO;
import xwsagent.wroomagent.domain.dto.LoginRequestDTO;
import xwsagent.wroomagent.domain.dto.SignupRequestDTO;
import xwsagent.wroomagent.domain.dto.UserDTO;
import xwsagent.wroomagent.exception.UsernameAlreadyExistsException;
import xwsagent.wroomagent.jwt.JwtTokenProvider;
import xwsagent.wroomagent.jwt.UserPrincipal;
import xwsagent.wroomagent.producer.MailProducer;
import xwsagent.wroomagent.repository.VerificationTokenRepository;
import xwsagent.wroomagent.repository.rbac.RoleRepository;
import xwsagent.wroomagent.repository.rbac.UserRepository;

@Service
public class AuthenticationService {

	
	private final AuthenticationManager authenticationManager;
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder encoder;
	private final JwtTokenProvider jwtProvider;
    private final MailProducer mailProducer;
	private final VerificationTokenRepository verificationRepository;

	public AuthenticationService(AuthenticationManager authenticationManager,
					   UserRepository userRepository,
					   RoleRepository roleRepository,
					   PasswordEncoder passwordEncoder,
					   JwtTokenProvider jwtProvider,
					   MailProducer mailProducer,
					   VerificationTokenRepository verificationRepository) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.encoder = passwordEncoder;
		this.jwtProvider = jwtProvider;
		this.mailProducer = mailProducer;
		this.verificationRepository = verificationRepository;
	}
	
	
	public LoggedUserDTO login(LoginRequestDTO request) {
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(request.getEmail(),
				request.getPassword());
		Authentication authentication = authenticationManager.authenticate(token);
		 
		String email = authentication.getName();
		List<String> authorities = authentication.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());
		
		String jwt = jwtProvider.generateToken(authentication);
		UserPrincipal user = (UserPrincipal)authentication.getPrincipal();
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return new LoggedUserDTO(user.getId(), email, authorities, jwt);
	}

	public boolean signup(SignupRequestDTO request) throws UsernameAlreadyExistsException {
		if (userRepository.existsByEmail(request.getEmail())) {
			throw new UsernameAlreadyExistsException("Username already exists!");
		}

		User user = new User(null, request.getName(), request.getSurname(), request.getEmail(),
				encoder.encode(request.getPassword()), new HashSet<RentRequest>(), null, new HashSet<Comment>(), null,
				Collections.singleton(roleRepository.findByName(RoleName.ROLE_USER)), false, true);

		user.setEnabled(false);
		user.setNonLocked(false);

		userRepository.save(user);
		
        String token = this.createVerificationToken(user);
		mailProducer.sendConfirmationMail(user.getEmail(), token);
		
		return true;
	}

	private String createVerificationToken(User user) {
		String token = UUID.randomUUID().toString();
        VerificationToken myToken = new VerificationToken(null,token,user,calculateExpiryDate());
        return verificationRepository.save(myToken).getToken();
    }
	
	private Date calculateExpiryDate() {
		int aDay = 1440;
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, aDay);
        return new Date(cal.getTime().getTime());
    }
	
	public LoggedUserDTO whoami(Authentication auth) {
		UserPrincipal user = (UserPrincipal) auth.getPrincipal();
		List<String> authorities = auth.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());
		
		String jwt = jwtProvider.generateToken(auth);
		return new LoggedUserDTO(user.getId(), user.getUsername(), authorities, jwt);
	}
	
	public UserDTO confirm(String token) {
		VerificationToken vt = this.verificationRepository.findByToken(token);
		User user = vt.getUser();
		user.setEnabled(true);
		this.userRepository.saveAndFlush(user);
		return UserConverter.fromEntity(user);
	}
	
	public void forgotPassword(String email) {
//		TODO: Generate GUID
		this.mailProducer.sendForgotPasswordEmail(email, "aldaldja");
	}
	
}
