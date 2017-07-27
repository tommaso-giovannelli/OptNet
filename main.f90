module scalatura_var
      logical :: scalatura
	  real*8,allocatable :: scala(:),x_orig(:)
end module scalatura_var

module vincoli
     integer m
     double precision, allocatable :: eps(:),constr(:),epsiniz(:)
	 double precision viol,violz
end module vincoli

program main_vincolato

      use scalatura_var
      use vincoli
      implicit none

      integer :: n
	  integer			 :: n_int

	  integer	:: icontr,numvar,j,istop,nobjn,icheck
	  integer, allocatable	:: index_int(:)
	  real		:: tbegin, tend

      integer ::i

      real*8, allocatable :: x(:),bl(:),bu(:),step(:)      


	  integer ::            num_funct,num_iter
	  real*8             :: f,alfamax,delta 
	  real*8			   :: fob,fapp
	  real*8			   :: violiniz, violint, finiz, fex
      real*8             :: alfa_stop
      integer            :: nf_max,iprint

!------------------------------------------------------------------------------

	  common /num/f
  	  common /calfamax/alfamax

!------------------------------------------------------------------------------

	  call setdim(n,m)
	  
	  allocate(index_int(n))
	  allocate(x(n),bl(n),bu(n),step(n))

      if(m.ge.1) then

	    allocate (constr(m),epsiniz(m),eps(m))

	  endif

              
 	  call cpu_time(tbegin)

	  call setbounds(n,bl,bu)
	  call startp(n,x)
	  call which_integer(n,bl,bu,index_int,step,x)


	  do i=1,n

         if((x(i).lt.bl(i)).or.(x(i).gt.bu(i))) then
		   write(*,*) ' starting point violates bound constraints'
		   stop
		 endif
     
	  enddo

2002  format(2d20.10)

!-----------------------------------------------------------------------
!     print starting point info
!-----------------------------------------------------------------------
      if(m.ge.1) then
	   
	    call funct_vinc(n,m,x,fob,constr)

        write(*,*) ' ------------------------------------------------- '
        write(1,*) ' ------------------------------------------------- '

        write(*,*) '      f(xo) = ',fob
        write(1,*) '      f(xo) = ',fob

        viol=0.d0

        do i = 1,m
	   	   viol=viol+max(0.d0,constr(i))
	    enddo

        write(*,*) '  cviol(xo) = ',viol
        write(1,*) '  cviol(xo) = ',viol


	  else

	  	call funct_vinc(n,m,x,fob,constr)

        write(*,*) ' ------------------------------------------------- '
        write(1,*) ' ------------------------------------------------- '

        write(*,*) '      f(xo) = ',fob
        write(1,*) '      f(xo) = ',fob

	  endif 

        write(*,*) ' ------------------------------------------------- '
        write(1,*) ' ------------------------------------------------- ' 		      
	    do i=1,n
		   write(*,*) ' xo(',i,') =',x(i)
		   write(1,*) ' xo(',i,') =',x(i)
        enddo


        write(*,*) ' ------------------------------------------------- '
        write(1,*) ' ------------------------------------------------- '




!-----------------------------------------------------------------------
!     variable scaling:
!	  ON   if scalatura = .TRUE.
!	  OFF  if scalatura = .FALSE.
!-----------------------------------------------------------------------
       scalatura=.false.

	   if(scalatura) then

	      allocate(scala(n),x_orig(n))

		  do i=1,n
		     if(index_int(i) .eq. 1) then
				scala(i) = 1.d0
		     else
				scala(i) = dmax1((dabs(bl(i))+dabs(bu(i)))/2.d0,1.d-16)
			 endif
		  enddo

		  do i=1,n
             x(i) = x(i)/scala(i)
             bl(i)=bl(i)/scala(i)
			 bu(i)=bu(i)/scala(i)
		  enddo


	   endif

!-----------------------------------------------------------------------



       if(scalatura) then
          do i=1,n
	         x_orig(i)=x(i)*scala(i)
		  enddo
          call funct_vinc(n,m,x_orig,fob,constr)
	   else
          call funct_vinc(n,m,x,fob,constr)
       endif


!-----------------------------------------------------------------------
!	choice of starting penalty parameter values
!-----------------------------------------------------------------------

	  do i = 1,m
	     if(max(0.d0,constr(i)) < 1.d-0) then
			eps(i) = 1.d-3
		 else
			eps(i) = 1.d-1
		 endif
	   enddo

	   num_funct   = 0 
	   num_iter    = 0

	   epsiniz     = eps
	   finiz       = fob
	   violiniz    = viol

1	   alfa_stop=1.d-6
  	   nf_max=1000
       iprint=0

      !-----------------------------------------------------------------------
      !     violation of integrality constraint of xo  
      !-----------------------------------------------------------------------

		violint=0.0d0
    
		do i=1,n

		   if(index_int(i)==1) then
		   		if((bl(i) > -1.d+6).and.(bu(i)) < 1.d+6) then
					violint=max( violint,abs(x(i)-bl(i)-( floor( ( x(i)-bl(i))/step(i)+0.5d0 )*step(i) ) )  )
		  
				elseif(bl(i) > -1.d+6) then
					violint=max( violint,abs(x(i)-bl(i)-( floor( ( x(i)-bl(i))/step(i)+0.5d0 )*step(i) ) )  )
		  
				elseif(bu(i) <  1.d+6) then
					violint=max( violint,abs(bu(i)-x(i)-( floor( (bu(i)-x(i))/step(i)+0.5d0 )*step(i) ) )  )
		  
				else
				   violint=max( violint,abs(x(i)-( floor( x(i)/step(i)+0.5d0 )*step(i) ) )  )
		  
				endif
			   
			endif   	   
		  
		end do

	   write(*,*)
	   write(*,*) ' call the optimizer ...'

       call sd_box(n,n_int,index_int,step,x,f,bl,bu,alfa_stop,nf_max,num_iter,num_funct,iprint,istop)

	   write(*,*) ' ... done'
	   write(*,*)

       if(scalatura) then
          do i=1,n
	         x(i)=x(i)*scala(i)
			 bl(i)=bl(i)*scala(i)
			 bu(i)=bu(i)*scala(i)
		  enddo
       endif

    
	 
	   call funct_vinc(n,m,x,fob,constr) 

	   call cpu_time(tend)

      
	   write(*,*) ' ------------------------------------------------------------------------------'     
	   write(1,*) ' ------------------------------------------------------------------------------'     
	   if(istop.eq.1) then
         write(*,*)  ' STOP - stopping criterion satisfied. alfa_max <= ',alfa_stop
         write(1,*)  ' STOP - stopping criterion satisfied. alfa_max <= ',alfa_stop
	   endif
       if(istop.eq.2) then
         write(*,*)  ' STOP - max number function evaluations exceeded. nf > ',nf_max
         write(1,*)  ' STOP - max number function evaluations exceeded. nf > ',nf_max
	   endif

       if(istop.eq.3) then
         write(*,*)  ' STOP - max number iterations exceeded. ni > ',nf_max
         write(1,*)  ' STOP - max number iterations exceeded. ni > ',nf_max
	   endif

	   write(*,*) ' total time:',tend-tbegin
	   write(1,*) ' total time:',tend-tbegin
       write(*,*) ' number of function evaluations = ',num_funct 
       write(1,*) ' number of function evaluations = ',num_funct     		    
	   write(*,*) ' ------------------------------------------------------------------------------'  
	   write(1,*) ' ------------------------------------------------------------------------------'  

		write(*,*)
		write(1,*)

        write(*,*) ' ------------------------------------------------- '
        write(1,*) ' ------------------------------------------------- '

        write(*,*) '     f(xstar) = ',fob
        write(1,*) '     f(xstar) = ',fob


        viol=0.d0

        do i = 1,m
	   	   viol=viol+max(0.d0,constr(i))
	    enddo

        write(*,*) ' cviol(xstar) = ',viol
        write(1,*) ' cviol(xstar) = ',viol


        write(*,*) ' ------------------------------------------------- '
        write(1,*) ' ------------------------------------------------- ' 		      
	    do i=1,n
		   write(*,*) ' xstar(',i,') =',x(i)
		   write(1,*) ' xstar(',i,') =',x(i)
        enddo

        write(*,*) ' ------------------------------------------------- '
        write(1,*) ' ------------------------------------------------- '

	   
	   write(*,*) 
	   write(1,*) 


      !-----------------------------------------------------------------------
      !     violation of integrality constraint of xstar 
      !-----------------------------------------------------------------------

		violint=0.0d0
    
		do i=1,n

		   if(index_int(i)==1) then
		   		if((bl(i) > -1.d+6).and.(bu(i)) < 1.d+6) then
					violint=max( violint,abs(x(i)-bl(i)-( floor( ( x(i)-bl(i))/step(i)+0.5d0 )*step(i) ) )  )
		  
				elseif(bl(i) > -1.d+6) then
					violint=max( violint,abs(x(i)-bl(i)-( floor( ( x(i)-bl(i))/step(i)+0.5d0 )*step(i) ) )  )
		  
				elseif(bu(i) <  1.d+6) then
					violint=max( violint,abs(bu(i)-x(i)-( floor( (bu(i)-x(i))/step(i)+0.5d0 )*step(i) ) )  )
		  
				else
				   violint=max( violint,abs(x(i)-( floor( x(i)/step(i)+0.5d0 )*step(i) ) )  )
		  
				endif
			   
			endif   	   
		  
		end do

end program main_vincolato
