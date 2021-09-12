import {Component, OnInit, Input} from '@angular/core';
import { AppService } from '../app.service';
import { GeolocationIp } from './geolocation-ip';


@Component({
  selector: 'app-geolocation-ip',
  templateUrl: './geolocation-ip.component.html',
  styleUrls: ['./geolocation-ip.component.css']
})
export class GeolocationIpComponent implements OnInit {

  errorMessage: string = "";

  hasError: boolean = false;

  @Input()
  requestIp!: string;

  localIp!: string;

  localGeolocation!: GeolocationIp

  requestedGeolocation!: GeolocationIp

  differenceInHours!: number;

  constructor(private appService: AppService) { }

 ngOnInit() {
  this.appService.getLocalIp().subscribe( 
      (data: any) => {
        if(data)
          this.localIp = data.query
      },
      (error: any) => {
        this.errorMessage = "Could not connect to ip-api.com";      
        this.hasError = true;
      }
    );
  }

  submit() {
    this.clearErrors();
    if(!this.requestIp || this.requestIp == ""){
      this.errorMessage = "The IP requested cannot be empty";
      this.hasError = true;
      return;
    }
    this.appService.retrieveGeolocation(this.localIp, this.requestIp).subscribe( 
      (data: any) => {
        if(!data) {
          this.hasError = true;
          this.errorMessage = "Something went wrong with the server. Data is empty";
          return;
        }
        this.mapToGeolocation(data);
        this.calculateDifference();
      },
      (error: any) => {
        this.hasError = true;
        this.errorMessage = "Could not connect to local api";
      }  
    );
  }
  
  mapToGeolocation(data : any){

    this.localGeolocation = data.geolocationIps[0] as GeolocationIp;
    this.requestedGeolocation = data.geolocationIps[1] as GeolocationIp;
  }

  calculateDifference() {
    let localDateTime = new Date(this.localGeolocation.dateTime);

    let requestedDateTime = new Date(localDateTime.
      toLocaleString('en-US', { timeZone: this.requestedGeolocation.timezone }));

    let diffMs = requestedDateTime.getTime() - localDateTime.getTime();
    var hours = (diffMs / 3600000);
    this.differenceInHours = Math.round(hours);
  }

  clearErrors(){
    this.hasError = false;
    this.errorMessage = "";
  }

}
