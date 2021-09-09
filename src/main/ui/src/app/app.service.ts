import { Injectable } from '@angular/core';
import { HttpClient,HttpParams, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AppService {

  constructor(private http: HttpClient) {
    this.getLocalIp();
    this.retrieveGeolocation();
   }

  localIp = "";
  requestIp = "1.1.1.1"
  rootURL = '/api';

  retrieveGeolocation() {

    const params = new HttpParams()
      .set('localIp', this.localIp)
      .set('requestIp', this.requestIp);
    this.http.get(this.rootURL + '/retrieveGeolocation', {params}).subscribe(response =>{
      console.log(response);
    });
  }

  async getLocalIp() {
    await this.http.get("http://ip-api.com/json").toPromise().then(data => {
    let geolocationInfo: any = data;
    return geolocationInfo.query;
    });

  }

}
