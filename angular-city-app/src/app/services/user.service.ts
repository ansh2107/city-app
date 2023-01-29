import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UserService {

 

  private baseUrl = 'http://localhost:8080';  
  
  constructor(private http: HttpClient) { }  

  checkForAdminUserRole(username: string, password: string) {
    const headers = new HttpHeaders({
      authorization : 'Basic ' + btoa(username + ':' + password)
    });
    headers.set('content-type', 'application/json');
    headers.set('Access-Control-Allow-Origin', '*');
    return this.http.get(`${this.baseUrl}/user/roles/${username}`, {headers});
  }

}
