import { AfterViewInit, Component, ElementRef, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { PageEvent } from '@angular/material/paginator';
import { Router } from '@angular/router';
import { debounceTime, distinctUntilChanged, filter, fromEvent, Subscription, tap } from 'rxjs';
import { City } from 'src/app/models/city.model';
import { CityService } from 'src/app/services/city.service';
import { EditCityDialogComponent } from '../edit-city-dialog/edit-city-dialog.component';

@Component({
  selector: 'app-city-list',
  templateUrl: './city-list.component.html',
  styleUrls: ['./city-list.component.css']
})
export class CityListComponent implements OnInit, AfterViewInit, OnDestroy {
  
  public cities: City[];
  public paginatedCities: City[];
  public searchedCityList: City[];
  public length = 10;
  public pageSize = 10;
  public pageIndex = 0;
  public pageSizeOptions = [5, 10, 25];
  public showFirstLastButtons = true;
  public searchModel: string = "";
  public isAllowEditing = false;

  public subscriptions = new Subscription();

  @ViewChild('input') searchInput!: ElementRef;
  
  public isSearchActive: boolean = false;

  constructor(private cityService: CityService, private dialog: MatDialog, private router: Router) {
    this.cities = [];
    this.paginatedCities = [];
    this.searchedCityList = [];
  }

  ngOnInit(): void {
    const userData: any = localStorage.getItem('userData');
    const parsedJsonData = JSON.parse(userData);
    if (parsedJsonData) {
      this.isAllowEditing = parsedJsonData.isAllowEditing;
      this.fetchCityData();
    } else {
      this.router.navigateByUrl('/login');
    }    
  }

  fetchCityData(): void {
    if (this.cities[(this.pageIndex*this.pageSize)]) {
      const index = this.pageIndex*this.pageSize;
      this.paginatedCities = this.cities.slice(index, index + this.pageSize);
      return;
    }
    this.subscriptions.add(this.cityService.getCitiesList(this.pageIndex, this.pageSize).subscribe((response)=>{
      console.log(response);
      if (response?.cities) {
        this.paginatedCities = response.cities;
        this.cities.push(...response.cities);
      }
      if (response?.totalCities) {
        this.length = response?.totalCities;
      }
      
    }, (error) => {
      console.log(error);
    }));
  }

  handlePageEvent(event: PageEvent) {
    this.length = event.length;
    this.pageSize = event.pageSize;
    this.pageIndex = event.pageIndex;
    this.fetchCityData();
  }

  openEditDialog(city: City): void {
    const dialogRef = this.dialog.open(EditCityDialogComponent, {
      data: city,
      width: '600px'
    });

    dialogRef.afterClosed().subscribe(result => {
      //console.log('The dialog was closed', result);
      if (result) {
        let city = this.cities.find(city => city.id === result.id);
        if (city) {
          city.name = result.name;
          city.photo = result.photo;
        }
        this.subscriptions.add(this.cityService.updateCityData(result).subscribe((response: any) => {
          //console.log("successfully updated");
          this.cityService.openSnackBar(response.message, 'dismiss');
        }));
      }
    });
  }

  ngAfterViewInit(): void {
    fromEvent(this.searchInput.nativeElement,'keyup')
            .pipe(
                filter(Boolean),
                debounceTime(150),
                distinctUntilChanged(),
                tap((text) => {
                  this.searchCity();
                })
            )
            .subscribe();
  }

  searchCity() {
    this.isSearchActive = true;
    console.log(this.searchInput.nativeElement.value);
    if (!this.searchInput.nativeElement.value) {
      this.searchedCityList = [];
      //this.fetchCityData();
      this.isSearchActive = false;
      return;
    }
    this.subscriptions.add(this.cityService.searchCityByName(this.searchInput.nativeElement.value).subscribe((response: any) => {
      if (response?.cities) {
        this.searchedCityList = response.cities;
      }
    }));
  }

  logout() {
    localStorage.clear();
    this.router.navigateByUrl('/login');
  }

  ngOnDestroy(): void {
    this.subscriptions?.unsubscribe();
  }
  

}
