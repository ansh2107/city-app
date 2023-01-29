import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { City } from '../models/city.model';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root'
})
export class CityService {

  private baseUrl = 'http://localhost:8080';  
  
  constructor(private http:HttpClient, private _snackBar: MatSnackBar) { }  
  
  updateCityData(city: City) {
    const headers = new HttpHeaders()
                        .set('content-type', 'application/json')
                        .set('Access-Control-Allow-Origin', '*');
    return this.http.post(`${this.baseUrl}/update-city/${city.id}`, city, { headers }); 
  }

  getCitiesList(page: number, size: number): Observable<any> {
    const headers = new HttpHeaders()
                        .set('content-type', 'application/json')
                        .set('Access-Control-Allow-Origin', '*');
    return this.http.get(`${this.baseUrl}/cities/${page}/${size}`, {headers});  
  }  

  searchCityByName(cityName: string) {
    const headers = new HttpHeaders()
                        .set('content-type', 'application/json')
                        .set('Access-Control-Allow-Origin', '*');
    return this.http.get(`${this.baseUrl}/cities/${cityName}`, {headers});  
  }

  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action);
  }

}
