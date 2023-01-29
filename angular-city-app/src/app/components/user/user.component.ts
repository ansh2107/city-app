import { Component, OnDestroy, OnInit } from '@angular/core';
import { Route, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})


export class UserComponent implements OnInit, OnDestroy {
    
  constructor(private userService: UserService, private router: Router) {}

  public username: string = "";

  public password: string = "";

  public errorMessage: string = "";

  public subscriptions = new Subscription();

  private ROLE_ALLOW_EDIT = "ROLE_ALLOW_EDIT";
  
  ngOnInit(): void {
    
  }

  onProceed() {
    this.errorMessage = "";
    this.subscriptions.add(this.userService.checkForAdminUserRole(this.username, this.password).subscribe((response: any) => {
      if (response?.roles) {
        let roles: [] = response.roles.split(",");
        let index = roles.findIndex(role => role === this.ROLE_ALLOW_EDIT);
        let isAllowEditing = false;
        if (index !== -1) {
          isAllowEditing = true;
        }
        localStorage.setItem('userData', JSON.stringify({data: response, isAllowEditing}));
        this.router.navigate(["/cities"]);
      }
    }, (error) => {
      console.log(error);
      this.errorMessage = "Wrong credentials or user does not exist";
    }));
  }


  ngOnDestroy(): void {
    this.subscriptions?.unsubscribe();
  }

}
