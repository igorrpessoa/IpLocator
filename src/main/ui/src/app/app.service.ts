import { Injectable } from '@angular/core';
import { HttpClient,HttpParams, HttpHeaders } from '@angular/common/http';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AppService {

  constructor(private http: HttpClient) {
   }

  rootURL = '/api';

  retrieveGeolocation(localIp : string, requestIp: string) {
    const params = new HttpParams()
      .set('localIp', localIp)
      .set('requestIp', requestIp);
    return this.http.get(this.rootURL + '/retrieveGeolocation', {params}).pipe(map((res : any)=> {
      return res;
    }));
  }

  getLocalIp() {
    return this.http.get("http://ip-api.com/json").pipe(map((res : any)=> {
      console.log(res);
      return res
    }));
  }

}
