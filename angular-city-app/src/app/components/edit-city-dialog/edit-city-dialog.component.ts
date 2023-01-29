import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { City } from 'src/app/models/city.model';
import * as _ from 'lodash';

@Component({
  selector: 'app-edit-city-dialog',
  templateUrl: './edit-city-dialog.component.html',
  styleUrls: ['./edit-city-dialog.component.css']
})

export class EditCityDialogComponent {
  
  dialogData: City;

  constructor(
    public dialogRef: MatDialogRef<EditCityDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: City,
  ) {

    this.dialogData = _.cloneDeep(this.data);

  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
