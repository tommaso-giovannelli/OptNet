subroutine setdim(n,m)
	implicit none
	integer	:: n ,m

	!----------------------------------------------
	! set the number of variables
	!----------------------------------------------
	n = 260

	!----------------------------------------------
	! set the number of inequality constraints
	! Be aware that an equality constraint h(x) = 0
	! must be written as h(x) <= 0 and -h(x) <= 0
	!----------------------------------------------
	m = 2

	return

end subroutine setdim


subroutine funct(n,x,f)

    use vincoli
	implicit none
	integer n,i
	real*8  x(n),f,fob,fmax

    call funct_vinc(n,m,x,fob,constr) 

	fmax = 0.d0
	viol = 0.d0

	do i = 1,m
		fmax = fmax + (max(0.d0,constr(i))**1.1d0)/eps(i)
		viol=viol+max(0.d0,constr(i))
	enddo

	f = fob + fmax

	return
end subroutine funct

      
subroutine funct_vinc(n, nc, x, f, constr)
   
	implicit none

	integer			 :: n, nc, iprint,m
	double precision :: x(n), f, constr(nc),fmed
	double precision :: r(2)   

	integer					:: i, j, nobj, icheck,x_integer,imed, summ
	
	character ( len = 64 )	:: nome_comando_batch

	character*12 	:: lettura_f
	character*14 	:: lettura_v
	character*30 	:: sincronizzazione
	character*100 	:: sincronizzazione_app
	character*5 	:: risposte
	character(len=5):: string_sincro_opt, string_sincro_sim
	
	logical			:: is_opened
	
    iprint=1

!--------------------------------------------        
!     inizio scrittura delle variabili
!--------------------------------------------
	   open(41, file='sim_legge.txt',status='old',action='write')
    
		do i=1,n
			x_integer = floor(x(i))
			write(41,fmt=12) x_integer 
		end do
           
		close(41)
		
12  format(I1)
!-----------------------------------------      
!      fine scrittura delle variabili     
!-----------------------------------------

	   if(iprint.ge.1) then
	      write(*,*) ' ' 
          write(*,*) ' ASSEGNAZIONE DELLE X ED AVVIO SIMULATORE'
	      write(*,*) ' '
       endif
 
 	! scrivi 'wait' sul file per la sincronizzazione dell'ottimizzatore
	string_sincro_opt = 'wait'
	open(41,file='sincroniz_opt.txt',status='old',action='write')
	write(41,fmt=10) string_sincro_opt
	close(41)
	

!---------------------------
!      avvio simulatore
!---------------------------

		string_sincro_sim = 'start'
3		inquire(file = 'sincroniz_sim.txt', opened = is_opened)
		if(is_opened) then
			call sleep1@(0.5)
			print*, ' file già aperto '
			go to 3
		end if
		open(42,file='sincroniz_sim.txt',status='old',action='write')
			write(42,fmt=10) string_sincro_sim
		close(42)	
     
    
!----------------------------
!      fine avvio simulatore
!----------------------------       

       if(iprint.ge.1) then
	      write(*,*) ' '
	   endif   

!-----------------------------------
!     attesa  simulatore
!----------------------------------
	  do  while(string_sincro_opt=='wait')
		call sleep1@(0.5)
		inquire(file = 'sincroniz_opt.txt', opened = is_opened)
		if(is_opened) then 
			print*, ' file già aperto '
			cycle
		end if
		
	    open(52,file='sincroniz_opt.txt',status= 'old',action='read')
		read(52,fmt=10) string_sincro_opt
		close(52)

      enddo  
!-----------------------------------
!     fine attesa simulatore
!-----------------------------------     



!-------------------------------------------
!     lettura uscite del simulatore
!-------------------------------------------   
		
		open(44,file='sim_scrive.txt',status='old',action='read')
	
			do i=1,2
				read(44,fmt=11)  r(i)
			end do
	
			if (r(1) == 1.d-6) then
				r(1) = 1.D0
			endif

		close(44)
     
      f=r(2)
      

	  WRITE(*,*) ' '
	  WRITE(*,*) ' funzione da minimizzare' 
	  write(*,121) f
121   format('f obiettivo =',D18.7)
      
      fmed=fmed+f

	  summ = SUM(x)
	
	  constr(1) = summ - 10.D0
	  constr(2) = 0.90 - r(1)
!-------------------------------------------
!     fine lettura uscite del simulatore
!-------------------------------------------   

	return

10 	format(a5)	
11  format(f11.6)
end subroutine funct_vinc



subroutine startp(n,x)
	implicit none
	integer		n,i
	real*8		x(n)

	!----------------------------------------------
	! this routine sets the starting point
	!----------------------------------------------
	
	do i = 1,10
		x(i) = 1.D0
	enddo
	
	do i = 11,n
		x(i) = 0.D0
	enddo

	return
end subroutine startp

subroutine setbounds(n,lb,ub)
	implicit none
	integer		n,i
	real*8		lb(n), ub(n)

	!----------------------------------------------
	! this routine sets the bounds on the variables
	!----------------------------------------------
    do i=1,n
		lb(i) = 0.0d0  
		ub(i) = 1.0d0
	enddo

	return
end subroutine setbounds

subroutine which_integer(n,A,B,is_integer,step,x)
!----------------------------------------------------
! this routine defines which variables are discrete,
! sets the smallest step between two consecutive 
! discrete values for all discrete variables and, 
! possibly, changes the starting point to make it 
! satisfy the integrality constraint
!----------------------------------------------------
	implicit none
	integer, intent(IN)	:: n
	real*8,  intent(INOUT)	:: A(n), B(n)
	integer, intent(OUT)	:: is_integer(n)
	real*8,  intent(OUT)	:: step(n)
	real*8,  intent(INOUT)	:: x(n)
	integer			:: i
	
	is_integer = 0
	step       = 0.d0
    
	do i=1,n
	  is_integer(i) = 1
	  step(i)       = 1.d0
	enddo
	return

end subroutine which_integer
