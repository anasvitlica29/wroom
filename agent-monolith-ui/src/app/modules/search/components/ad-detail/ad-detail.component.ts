import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { AdDetailDialogData } from './dialog-data.model';
import { AdService } from '../../service/ad.service';
import { ToastrService } from 'ngx-toastr';
import { Ad } from '../../model/ad.model';
import { VehicleService } from '../../service/vehicle.service';
import { Vehicle } from '../../model/vehicle.model';
import { PricelistDetailDialogData } from '../price-details/pricelist-dialog-data.model';
import { PriceList } from '../../model/price-list.model';
import { CommentsService } from 'src/app/modules/shared/service/comments.service';
import { Comment } from 'src/app/modules/shared/models/comment.model';
import { AuthService } from 'src/app/modules/auth/service/auth.service';
import { NewReplyComponent } from 'src/app/modules/ads/comments/new-reply/new-reply.component';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-ad-detail',
  templateUrl: './ad-detail.component.html',
  styleUrls: ['./ad-detail.component.css']
})
export class AdDetailComponent implements OnInit {

  ad: Ad;
  vehicle: Vehicle;
  images: string[] = [];
  comments: Comment[] = [];
  currentImageIndex: number = 0;
  currentImage: string = "";
  loaded: boolean = false;
  isOwner: boolean = false;

  priceData: PricelistDetailDialogData = {
    pricelistId: null,
    mileLimit: null,
    cdw: null
  };

  constructor(public dialogRef: MatDialogRef<AdDetailComponent>,
    @Inject(MAT_DIALOG_DATA) public data: AdDetailDialogData,
    private adService: AdService,
    private commentsService: CommentsService,
    private authService: AuthService,
    private vehicleService: VehicleService,
    private toastr: ToastrService,
    public sanitizer: DomSanitizer,
    private dialog: MatDialog,) { }

  ngOnInit(): void {
    this.fetchAd();
  }

  fetchAd() {
    this.adService.get(this.data.adID).subscribe(
      data => {
        this.ad = data;
        this.ad.priceListObj = this.data.pricelist;
        console.log(this.ad)
        this.fetchVehicle();
      },
      error => {
        this.toastr.error('There was an unexpected error during fetching choosen ad.', 'Error')
      }
    );
  }

  fetchVehicle() {
    this.vehicleService.get(this.ad.vehicleId).subscribe(
      data => {
        this.vehicle = data;
        this.fetchImages();
      },
      error => {
        this.toastr.error('There was an unexpected error.', 'Vehicle')
      }
    );

  }

  fetchImages() {
    this.vehicleService.getVehicleImages(this.ad?.vehicleId).subscribe(
      data => {
        data.forEach(obj => { this.images.push("data:image/jpeg;base64," + obj) });
        this.currentImage = this.images[0];
        this.initPricelist();
      },
      error => {
        this.toastr.error('There was an error.', 'Images');
      }
    );
  }

  initPricelist() {
    this.priceData.pricelistId = this.ad?.priceListId;
    this.priceData.mileLimit = this.ad?.mileLimit;
    this.priceData.cdw = this.vehicle?.cdw;
  }

  public getSantizeUrl() {
    return this.sanitizer.bypassSecurityTrustUrl(this.currentImage);
  }

  leftImageClick() {
    if (this.currentImageIndex > 0) {
      this.currentImageIndex = this.currentImageIndex - 1;
      this.currentImage = this.images[this.currentImageIndex];
    }
  }

  rightImageClick() {
    if (this.currentImageIndex < this.images.length - 1) {
      this.currentImageIndex = this.currentImageIndex + 1;
      this.currentImage = this.images[this.currentImageIndex];
    }
    else if (this.currentImageIndex == this.images.length - 1) {
      this.currentImageIndex = 0;
      this.currentImage = this.images[0];
    }
  }

  onTabClick(event: any) {
    if (event.index == 1) {
      this.commentsService.getAll(this.ad.id).subscribe(
        data => {

          this.comments = data;

          for(let c of this.comments) {
            console.log(c)
            if(c.replyId != null) {
              const reply = this.comments.find(obj => {return obj.id === c.replyId});
              if(reply != null) {
                c.replyObj = reply;
                const i = this.comments.indexOf(c.replyObj);
                this.comments.splice(i, 1);
              }
            }
          }

          console.log('komentari posle fora',this.comments)

          // check if logged user is the owner
          this.authService.loggedUserSubject.subscribe(
            data => {
              this.loaded = true;
              if(data?.id == this.ad.ownerId) {
                this.isOwner = true;
              }
            }
          )
          
        },
        error => {
          this.loaded = true;
          this.toastr.error('An unexpected error has occurred.', 'Comments')
        }
      )
    }
  }

  addReply(ad: Ad, comment: Comment){
    const dialogRef = this.dialog.open(NewReplyComponent, {
      width: '500px',
      height: '300px',
      data: {ad, comment}
    });
    dialogRef.afterClosed().subscribe(result => {
      this.fetchAd();
    });
  }

}
