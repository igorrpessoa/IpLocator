import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GeolocationIpComponent } from './geolocation-ip.component';

describe('GeolocationIpComponent', () => {
  let component: GeolocationIpComponent;
  let fixture: ComponentFixture<GeolocationIpComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GeolocationIpComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GeolocationIpComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
