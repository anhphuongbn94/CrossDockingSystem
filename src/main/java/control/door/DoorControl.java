package control.door;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DoorControl {
//	@Autowired
//	private DoorDAO doorDAO;
	
	/* Income Door
	 * 
	 * */
	@RequestMapping(value = "indoor", method = RequestMethod.GET)
	public ModelAndView in_door(){
		return new ModelAndView("views/in_door");
	}
	/* Outcome Door
	 * 
	 * */
	@RequestMapping(value = "outdoor", method = RequestMethod.GET)
	public ModelAndView out_door(){
		return new ModelAndView("views/out_door");
	}
}
