package com.nt.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nt.model.Admin;
import com.nt.model.Candidate;
import com.nt.model.User;
import com.nt.service.AdminService;
import com.nt.service.CandidateService;
import com.nt.service.RoleService;
import com.nt.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private CandidateService cndServ;
	
	@Autowired
	private AdminService admServ;
	
	@Autowired
	private UserService userServ;
	
	@Autowired
	private RoleService roleServ;
		
		
//=======================================================================================================================================	
	@RequestMapping("")
	public String dashboard(Model m,Principal p)
	{
		
		// view total number of votes
		int c1 = 0;
		int c2 = 0;
		int c3 = 0;
		int c4 = 0;

		List<Candidate> listc = cndServ.getAllCandidates();
		for (Candidate v : listc) {
			if (!v.getCandidate1().equals(""))
				c1++;
			if (!v.getCandidate2().equals(""))
				c2++;
			if (!v.getCandidate3().equals(""))
				c3++;
			if (!v.getCandidate4().equals(""))
				c4++;
		}

		System.out.println(c1);
		System.out.println(c2);
		System.out.println(c3);
		System.out.println(c4);

		m.addAttribute("c1", c1);
		m.addAttribute("c2", c2);
		m.addAttribute("c3", c3);
		m.addAttribute("c4", c4);
		
		m.addAttribute("title","Admin Home");
		
		return "admin/dashboard";
	}
	
//=======================================================================================================================================	
	
	// viewadmins
	@GetMapping("/viewadmins")
				@RequestMapping("viewadmins")
				public String viewadmins(Model m)
				{
					String dest = "viewadmins";
					
					
					List<Admin> admins = admServ.getAllAdmin();
					
					m.addAttribute("admins", admins);
					m.addAttribute("title","View Admins");
					
					return "admin/viewadmins";
				}
//=======================================================================================================================================	
	
	// viewusers
			@GetMapping("viewusers")
			public String viewuser(Model m)
			{
				
				
				List<User> users = userServ.getAllUsers();
				
				m.addAttribute("users", users);
				m.addAttribute("title","All Voter's Details");
				
				return "admin/viewusers";
			}
				
			
			// view user which is to update
			@GetMapping("/edituser/{id}")
			public String edit(@PathVariable int id,Model m)
			{
				User user = userServ.getUserById(id);
					
				m.addAttribute("user",user);
				
				return "admin/edituser";
			}
			
			// update user
			@PostMapping("/updateuser")
			public String updateemp(@ModelAttribute User user,HttpServletRequest  session)
			{
				userServ.addUser(user);
				
				session.setAttribute("msg", "User updated successfully...");
				
				return "redirect:/admin/viewusers";
			}
				
			// delete user
			@GetMapping("/deleteuser/{id}")
			public String deleteemp(@PathVariable int id,HttpSession session)
			{
				userServ.deleteUser(id);
				
				session.setAttribute("msg", "User deleted successfully...");
				
				return "redirect:/admin/viewusers";
			}
			
//=======================================================================================================================================	
				
			// viewcandidates
						@GetMapping("votedetails")
						public String viewcandidates(Model m)
						{
							
							List<Candidate> candidates = cndServ.getAllCandidates();
						
							m.addAttribute("candidates", candidates);
							m.addAttribute("title","All Votes Details");
							
							
							// view total number of votes
							int c1=0;
							int c2=0;
							int c3=0;
							int c4=0;
							
							List<Candidate> listc = cndServ.getAllCandidates();
							for(Candidate v : listc)
							{
								if(!v.getCandidate1().equals(""))
									c1= c1 + 1;
								if(!v.getCandidate2().equals(""))
									c2= c2 + 1;
								if(!v.getCandidate3().equals(""))
									c3= c3 + 1;
								if(!v.getCandidate4().equals(""))
									c4= c4 + 1;
							}
							
							m.addAttribute("c1",c1);
							m.addAttribute("c2",c2);
							m.addAttribute("c3",c3);
							m.addAttribute("c4",c4);
							
							
							return "admin/votedetails";
						}
						
						
			
						// deletecandidate
						
						@GetMapping("/deletecandidate/{id}")
						public String deleteVote(@PathVariable("id") int id,HttpSession session)
						{
							
							cndServ.deleteCandidate(id);
							session.setAttribute("msg", "Vote Deleted Sucessfully");
							
							return "redirect:/admin/votedetails";
						}			
			
			
			
			
	
//=======================================================================================================================================	
	
}
