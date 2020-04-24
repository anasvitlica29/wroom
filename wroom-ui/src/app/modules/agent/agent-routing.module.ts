import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';
import { HomeAgentComponent } from './home-agent/home-agent.component';
import { AgentComponent } from './agent.component';

const routes: Routes = [
  {
    path:'', 
    component: AgentComponent,
    children: [
      // You add your paths here so when the agent signs in
      // he gets his links. Example: 
      // http://wroom.com/agent ---> Register a Car ---> http://wroom.com/agent/register-car
      { path: 'home', component: HomeAgentComponent }
    ]
  }
];

@NgModule({
  declarations: [],
  imports: [
    RouterModule.forChild(routes),
    CommonModule
  ],
  exports: [
    RouterModule
  ]
})
export class AgentRoutingModule { }
