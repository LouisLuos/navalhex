import { Routes } from '@angular/router';
import { Register } from './pages/register/register';
import { Login } from './pages/login/login';
import { Dashboard } from './pages/dashboard/dashboard';
import { Onboarding } from './pages/onboarding/onboarding';
import { PublicTenant } from './pages/public-tenant/public-tenant';

export const routes: Routes = [
    { path: 'register', component: Register },
    { path: 'login', component: Login },
    { path: 'dashboard', component: Dashboard },
    { path: 'onboarding', component: Onboarding },
    { path: ':slug', component: PublicTenant },
    { path: '', redirectTo: 'login', pathMatch: 'full' }
];


