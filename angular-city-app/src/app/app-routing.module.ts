import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CityListComponent } from './components/city/city-list.component';
import { UserComponent } from './components/user/user.component';

const routes: Routes = [  
  { path: '', redirectTo: 'login', pathMatch: 'full' },  
  { path: 'cities', component: CityListComponent },
  { path: 'login', component: UserComponent }
];  
  
@NgModule({  
  imports: [RouterModule.forRoot(routes)],  
  exports: [RouterModule]  
})  

export class AppRoutingModule { }
