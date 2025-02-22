import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {NgForOf, NgIf} from '@angular/common';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  standalone: true,
  imports: [
    NgIf,
    NgForOf
  ],
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {
  products : any;
  constructor(private http:HttpClient) { }

  ngOnInit(): void {
    this.http.get("http://localhost:9999/inventory-service/products?projection=fullProduct")
      .subscribe({
        next : (data)=>{
          this.products=data;
        },
        error : (err)=>{}
      });
  }

}
