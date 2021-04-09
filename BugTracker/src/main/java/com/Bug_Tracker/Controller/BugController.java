package com.Bug_Tracker.Controller;
import com.Bug_Tracker.Model.Bug;
import com.Bug_Tracker.Model.HttpResponse;
import com.Bug_Tracker.exception.domain.*;
import com.Bug_Tracker.service.BugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import static org.springframework.http.HttpStatus.OK;


import java.io.IOException;
import java.util.List;
@RestController
@RequestMapping(path = {"/" ,"/bug"})
public class BugController
{
        private BugService bugService;

        
        @Autowired
        public BugController(BugService bugService) {
            this.bugService = bugService;

        }

        @PostMapping("/addBug")
        //@RequestParam is for form data
        @PreAuthorize("hasAnyAuthority('user:create')") // only authenticated users with create privilege can add a bug
        public ResponseEntity<Bug> addNewBug(@RequestParam("description") String description,
                                               @RequestParam("priority") String priority,
                                               @RequestParam("bugType") String bugType,
                                               @RequestParam("systemBug") String systemBug,
                                               @RequestParam("isActive") String isActive,
                                             @RequestParam("bugName") String bugName)
                throws IOException {

            Bug newBug = bugService.addNewBug (bugName, description, priority, bugType, systemBug, Boolean.parseBoolean(isActive));
            return new ResponseEntity<>(newBug, OK);
        }

        @PostMapping("/updateBug")
        @PreAuthorize("hasAnyAuthority('user:update')")
        public ResponseEntity<Bug> updateBug(@RequestParam("currentBugName") String name,
                                             @RequestParam("newBugName") String newBugName,
                                             @RequestParam("description") String description,
                                             @RequestParam("priority") String priority,
                                             @RequestParam("bugType") String bugType,
                                             @RequestParam("systemBug") String systemBug,
                                             @RequestParam("isActive") String isActive)// throws BugnameNotFoundException, BugnameExistException {
        {
            Bug updatedBug = bugService.updateBug(name,newBugName,description, priority, bugType, systemBug, Boolean.parseBoolean(isActive));
            return new ResponseEntity<>(updatedBug, OK);
        }



        @GetMapping("/list")
        @PreAuthorize("hasAnyAuthority('user:read')")
        public ResponseEntity<List<Bug>> getAllBugs() {
            List<Bug> bugs = bugService.getBugs();
            return new ResponseEntity<>(bugs, OK);
        }

        @DeleteMapping("/delete/{bugname}") // pathvariable is for param data
        @PreAuthorize("hasAnyAuthority('user:delete')")
        public ResponseEntity<HttpResponse> deleteBug(@PathVariable("bugname") Long id) throws IOException {
            bugService.deleteBug(id);
            return response(OK, "BUG_DELETED_SUCCESSFULLY");
        }


        private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
            return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                    message), httpStatus);
        }


}


