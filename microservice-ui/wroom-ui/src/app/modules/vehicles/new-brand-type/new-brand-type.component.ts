import { Component, OnInit, Inject, Optional } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { BrandType } from 'src/app/modules/shared/models/brand-type.model';
import { BrandTypeService } from '../services/vehicle-features/brand-type.service';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FeaturesOverviewComponent } from '../features-overview/features-overview.component';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-new-brand-type',
  templateUrl: './new-brand-type.component.html',
  styleUrls: ['./new-brand-type.component.css']
})
export class NewBrandTypeComponent implements OnInit {
  createForm: FormGroup;
  success = false;
  errorMessage = false
  brandType: BrandType = new BrandType();
  new: BrandType = new BrandType();
  messageError: any;

  constructor(private formBuilder: FormBuilder, private brandTypeService: BrandTypeService,
    private brandService: BrandTypeService,
    private toastr: ToastrService,
    public dialogRef: MatDialogRef<FeaturesOverviewComponent>,
    //@Optional() is used to prevent error if no data is passed
    @Optional() @Inject(MAT_DIALOG_DATA) public data: any) { }

  local_data = this.data;

  ngOnInit(): void {
    this.createForm = this.formBuilder.group({
      name: new FormControl(null, Validators.required)
    });
  }

  save() {
    this.brandTypeService.create(this.brandType)
      .subscribe(data => {
      this.toastr.success('You have successfully added Brand Type!', 'Success')
    },
    error =>{ 
      this.toastr.error(error.errors, 'Error')});
  }

  onSubmit() {
    this.brandType.name = this.createForm.value.name;
    this.save();
  }

  back(){
    this.errorMessage = false;
    this.success = false;

  }

  onSubmitUpdate(id: number){
    this.brandTypeService.update(id,this.local_data).subscribe(
      data => {
        this.new.name = this.brandUpdateForm.value.name;
      },
      error => console.log('Error!'));

  }

  brandUpdateForm = new FormGroup({
    name: new FormControl({ value: this.local_data.name, disabled: false}, Validators.required)
  });


}
