!============================================================================================
!    DFLgen - Derivative-Free Linesearch program for Mixed Integer Nonlinear Programming 
!    Copyright (C) 2011  G.Liuzzi, S.Lucidi, F.Rinaldi
!
!    This program is free software: you can redistribute it and/or modify
!    it under the terms of the GNU General Public License as published by
!    the Free Software Foundation, either version 3 of the License, or
!    (at your option) any later version.
!
!    This program is distributed in the hope that it will be useful,
!    but WITHOUT ANY WARRANTY; without even the implied warranty of
!    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
!    GNU General Public License for more details.
!
!    You should have received a copy of the GNU General Public License
!    along with this program.  If not, see <http://www.gnu.org/licenses/>.
!
!    G.Liuzzi, S.Lucidi, F.Rinaldi. Derivative-free methods for mixed-integer optimization, 
!    TR IASI n. R11-08, 2011.
!============================================================================================
      subroutine sd_box(n,n_int,index_int,step,x,f,bl,bu,alfa_stop,nf_max,ni,nf,iprint,istop)
      use scalatura_var
	  use vincoli
      implicit none
	  logical :: cambio_eps
      integer :: n,i,j,i_corr,nf,ni,nf_max,n_int,index_int(n)
      integer :: num_fal,istop
      integer :: iprint,i_corr_fall
	  integer :: flag_fail(n)

      real*8 :: x(n),z(n),d(n)
      real*8 :: alfa_d(n),alfa,alfa_max, alfa_d_old
      real*8 :: f,fz , eta, fob
	  real*8 :: bl(n),bu(n),alfa_stop,maxeps,step(n) 
	  logical:: discr_change
      real*8 :: fstop(n+1)



	  discr_change = .false. 

	  eta = 1.d-6

      flag_fail=0

	  num_fal=0

      istop = 0

      fstop=0.d0

      do i=1,n

        if(index_int(i).eq.0) then
        
           alfa_d(i)=dmax1(1.d-3,dmin1(1.d0,dabs(x(i))))
      
           if(iprint.ge.1) then
              write(*,*) ' alfainiz(',i,')=',alfa_d(i)
              write(1,*) ' alfainiz(',i,')=',alfa_d(i)
           endif
		else

		   alfa_d(i)=dmax1(step(i),dmin1(2.d0*step(i),dabs(x(i))))
      
           if(iprint.ge.1) then
              write(*,*) ' alfainiz(',i,')=',alfa_d(i)
              write(1,*) ' alfainiz(',i,')=',alfa_d(i)
           endif
		end if
      end do

      do i=1,n      
        d(i)=1.d0 
      end do
     
      if(scalatura) then
         do i=1,n
	        x_orig(i)=x(i)*scala(i)
	     enddo
         call funct(n,x_orig,f)
	  else
         call funct(n,x,f)
      endif

	  nf=nf+1

	  i_corr=1

      fstop(i_corr)=f

      do i=1,n
	    z(i)=x(i)
      end do

      if(iprint.ge.2) then
        write(*,*) ' ----------------------------------'
        write(1,*) ' ----------------------------------'
        write(*,*) ' finiz =',f
        write(1,*) ' finiz =',f
        do i=1,n
          write(*,*) ' xiniz(',i,')=',x(i)
          write(1,*) ' xiniz(',i,')=',x(i)
        enddo
      endif


      do 

         if(iprint.ge.1) then
           write(*,*) '----------------------------------------------'
           write(1,*) '----------------------------------------------'
           write(*,100) ni,nf,f,alfa_max
           write(1,100) ni,nf,f,alfa_max
100        format(' ni=',i4,'  nf=',i5,'   f=',d12.5,'   alfamax=',d12.5)
         endif
         if(iprint.ge.2) then
	       do i=1,n
                write(*,*) ' x(',i,')=',x(i)
                write(1,*) ' x(',i,')=',x(i)
            enddo
         endif
         if(index_int(i_corr).eq.0) then 
 
                call linesearchbox_cont(n,step,x,f,d,alfa,alfa_d,z,fz,i_corr,num_fal,&
                           alfa_max,i_corr_fall,iprint,bl,bu,ni,nf)

         else
                alfa_d_old=alfa_d(i_corr)
                call linesearchbox_discr(n,eta,index_int,step,x,f,d,alfa,alfa_d,z,fz,i_corr,num_fal,&
                           alfa_max,i_corr_fall,iprint,bl,bu,ni,nf,discr_change,flag_fail)

         endif

         if(dabs(alfa).ge.1.d-12) then
		    
			flag_fail(i_corr)=0
		               
            x(i_corr) = x(i_corr)+alfa*d(i_corr)
            f=fz
			viol=violz
 	        fstop(i_corr)=f
			     
            num_fal=0
            ni=ni+1
      
         else

			flag_fail(i_corr)=1

			if ((index_int(i_corr).eq.1).and.(alfa_d_old.gt.step(i_corr)))  flag_fail(i_corr)=0

	        if(i_corr_fall.lt.2) then 

		      fstop(i_corr)=fz         

              num_fal=num_fal+1
              ni=ni+1

	        endif

	     end if

		 z(i_corr) = x(i_corr)

         if(i_corr.lt.n) then
            i_corr=i_corr+1
         else
		    if(.not.discr_change) then
				do i = 1,n
					if((index_int(i).eq.1).and.(alfa_d(i) > 1)) then
						discr_change = .true.
						exit
					endif
				enddo
				if(.not.discr_change) then
					eta = eta/2.d0
				endif
			endif
            i_corr=1
	   	    discr_change = .false. 
         end if 

         call stop(n,n_int,index_int,step,alfa_d,istop,alfa_max,nf,ni,fstop,f,alfa_stop,nf_max,flag_fail)

         if (istop.ge.1) exit


		 if(scalatura) then
              do j=1,n
	              x_orig(j)=x(j)*scala(j)
              enddo
              
              call funct(n,x_orig,f)
              call funct_vinc(n, m, x_orig, fob, constr)
		  else
			  call funct(n,x,f)
              call funct_vinc(n, m, x, fob, constr)
		  endif
	     
		 
		 if(viol.gt.0.d0) then 

           cambio_eps=.false.
    	   maxeps = maxval(eps)

	       do i = 1,m
		    if(eps(i) == maxeps) then
		      if(eps(i) > 1.0d-2*sqrt(alfa_max)) then
				 eps(i) =min(1.d-2*eps(i), 1.0d-1*sqrt(alfa_max))

			   	 write(1,*) '**************************************'
			  	 write(*,*) '**************************************'
				 write(*,*) '*********** aggiorno eps(',i,')=',eps(i),' *************'
				 write(1,*) '*********** aggiorno eps(',i,')=',eps(i),' *************'
				 write(*,*) '**************************************'
				 write(1,*) '**************************************'
				 cambio_eps=.true.
               endif
		     endif
	       enddo
           if(cambio_eps) then
		     if(scalatura) then
              do j=1,n
	              x_orig(j)=x(j)*scala(j)
              enddo
              call funct(n,x_orig,f)
			 else
			  call funct(n,x,f)
			 endif
			 

			 write(*,*) ' nuovo valore funzione=',f
			 write(1,*) ' nuovo valore funzione=',f
			 write(*,*) ' fob = ',fob
			 write(*,*) 'viol = ',viol

             do i=1,n
                if(index_int(i).eq.0) then
        
                  alfa_d(i)=dmax1(1.d-3,dmin1(1.d0,dabs(x(i))))
      
                  if(iprint.ge.1) then
                    write(*,*) ' alfainiz(',i,')=',alfa_d(i)
                    write(1,*) ' alfainiz(',i,')=',alfa_d(i)
                  endif
		        else

		          alfa_d(i)=dmax1(step(i),dmin1(2.d0*step(i),dble( floor(dabs(x(i)))/step(i) )*step(i) )) 
				       
                  if(iprint.ge.1) then
                    write(*,*) ' alfainiz(',i,')=',alfa_d(i)
                    write(1,*) ' alfainiz(',i,')=',alfa_d(i)
                  endif
		       end if
             end do
           endif
		 endif
      enddo
      return
    


      end
        


!     #######################################################

      subroutine stop(n, n_int,index_int,step, alfa_d,istop,alfa_max,nf,ni,fstop,f,alfa_stop,nf_max, flag_fail)
      use scalatura_var
      implicit none
      
      integer :: n,istop,i,nf,ni,nf_max
	  integer :: n_int,index_int(n), flag_fail(n)

      real*8 :: alfa_d(n),alfa_max,fstop(n+1),ffstop,ffm,f,alfa_stop
	  real*8 :: step(n) 

	  logical :: test

      istop=0

      alfa_max=1.0d0


      do i=1,n				 
	    if (index_int(i).eq.0) then 
          if(alfa_d(i).gt.alfa_max) then
            alfa_max=alfa_d(i)
          end if
		end if
      end do
     
      if(ni.ge.(n+1)) then
        ffm=f
        do i=1,n
          ffm=ffm+fstop(i)
        enddo
        ffm=ffm/dfloat((n+1))

        ffstop=(f-ffm)*(f-ffm)
        do i=1,n
           ffstop=ffstop+(fstop(i)-ffm)*(fstop(i)-ffm)
        enddo
 
        ffstop=dsqrt(ffstop/dfloat(n+1))


	  endif

        
      if(alfa_max.le.alfa_stop) then
	    test=.true.
		do i=1,n
		
         if (index_int(i).eq.1) then 
        
		  if((alfa_d(i).ne.step(i)).or.(flag_fail(i).ne.1)) then
		    test=.false.
	      end if
        
		 end if
	  

		end do
        if (test.eqv..true.) then
		   istop = 1
		end if
        
	  end if
      


      if(nf.gt.nf_max) then
        istop = 2
      end if

      if(ni.gt.nf_max) then
        istop = 3
      end if

      return

      end


!     ********************************************************
           
 
      subroutine linesearchbox_cont(n,step,x,f,d,alfa,alfa_d,z,fz,i_corr,num_fal,&
                                 alfa_max,i_corr_fall,iprint,bl,bu,ni,nf)
      
      use scalatura_var
	  use vincoli
      implicit none

      integer :: n,i_corr,nf
      integer :: i,j
      integer :: ni,num_fal
      integer :: iprint,i_corr_fall
	  integer :: ifront,ielle
      real*8 :: x(n),d(n),alfa_d(n),z(n),bl(n),bu(n),step(n)
      real*8 :: f,alfa,alfa_max,alfaex, fz,gamma, gamma_int
      real*8 :: delta,delta1,fpar,fzdelta,violzdelta

	  
	  gamma=1.d-6    

      delta =0.5d0
      delta1 =0.5d0

      i_corr_fall=0

	  ifront=0


      j=i_corr

	  if(iprint.ge.1) then
			write(*,*) 'variabile continua  j =',j,'    d(j) =',d(j),' alfa=',alfa_d(j)
			write(1,*) 'variabile continua  j =',j,'    d(j) =',d(j),' alfa=',alfa_d(j)
	  endif


	  if(dabs(alfa_d(j)).le.1.d-3*dmin1(1.d0,alfa_max)) then
			alfa=0.d0
			if(iprint.ge.1) then
				 write(*,*) '  alfa piccolo'
				 write(1,*) '  alfa piccolo'
				 write(*,*) ' alfa_d(j)=',alfa_d(j),'    alfamax=',alfa_max
				 write(1,*) ' alfa_d(j)=',alfa_d(j),'    alfamax=',alfa_max
			endif
			return
	  endif
      

	  do ielle=1,2

		 if(d(j).gt.0.d0) then

		     if((alfa_d(j)-(bu(j)-x(j))).lt.(-1.d-6)) then                 
   			    alfa=dmax1(1.d-24,alfa_d(j))
			 else
			    alfa=bu(j)-x(j)
				ifront=1
				if(iprint.ge.1) then
					   write(*,*) ' punto espan. sulla front. *'
					   write(1,*) ' punto espan. sulla front. *'
				endif
			 endif

		  else

			 if((alfa_d(j)-(x(j)-bl(j))).lt.(-1.d-6)) then
			    alfa=dmax1(1.d-24,alfa_d(j))
			 else
				alfa=x(j)-bl(j)
				ifront=1
				if(iprint.ge.1) then
					   write(*,*) ' punto espan. sulla front. *'
					   write(1,*) ' punto espan. sulla front. *'
				endif
			 endif

		  endif

		  if(dabs(alfa).le.1.d-3*dmin1(1.d0,alfa_max)) then
  
			 d(j)=-d(j)
			 i_corr_fall=i_corr_fall+1
			 ifront=0

			 if(iprint.ge.1) then
				   write(*,*) ' direzione opposta per alfa piccolo'
				   write(1,*) ' direzione opposta per alfa piccolo'
				   write(*,*) ' j =',j,'    d(j) =',d(j)
				   write(1,*) ' j =',j,'    d(j) =',d(j)
				   write(*,*) ' alfa=',alfa,'    alfamax=',alfa_max
				   write(1,*) ' alfa=',alfa,'    alfamax=',alfa_max
			  endif
			  alfa=0.d0
			  cycle

		  endif

		  alfaex=alfa

		  z(j) = x(j)+alfa*d(j)
    
		  if(scalatura) then
			   do i=1,n
				  x_orig(i)=z(i)*scala(i)
			   enddo
			   call funct(n,x_orig,fz)
			   violz=viol
		  else
			   call funct(n,z,fz)
			   violz=viol
		  endif

		  nf=nf+1

		  if(iprint.ge.1) then
				write(*,*) ' fz =',fz,'   alfa =',alfa
				write(1,*) ' fz =',fz,'   alfa =',alfa
		  endif
		  if(iprint.ge.2) then
			  do i=1,n
				  write(*,*) ' z(',i,')=',z(i)
				  write(1,*) ' z(',i,')=',z(i)
			  enddo
		  endif

		  fpar= f-gamma*alfa*alfa


		  if(fz.lt.fpar) then


			 do

				  if((ifront.eq.1)) then

			         if(iprint.ge.1) then
				         write(*,*) ' accetta punto sulla frontiera fz =',fz,'   alfa =',alfa
				         write(1,*) ' accetta punto sulla frontiera fz =',fz,'   alfa =',alfa
			         endif
				     alfa_d(j)=delta*alfa

				     return

				 end if

				 if(d(j).gt.0.d0) then
							
					 if((alfa/delta1-(bu(j)-x(j))).lt.(-1.d-6)) then
						 alfaex=alfa/delta1
					 else
						 alfaex=bu(j)-x(j)
						 ifront=1
						 if(iprint.ge.1) then
							write(*,*) ' punto espan. sulla front.'
							write(1,*) ' punto espan. sulla front.'
						 endif
					 end if

				 else

					 if((alfa/delta1-(x(j)-bl(j))).lt.(-1.d-6)) then
						 alfaex=alfa/delta1
					 else
						 alfaex=x(j)-bl(j)
						 ifront=1
						 if(iprint.ge.1) then
							write(*,*) ' punto espan. sulla front.'
							write(1,*) ' punto espan. sulla front.'
						 endif
					 end if

				 endif
						 
				 z(j) = x(j)+alfaex*d(j) 
				   
     
				 if(scalatura) then
					  do i=1,n
						 x_orig(i)=z(i)*scala(i)
					  enddo
					  call funct(n,x_orig,fzdelta)
			          violzdelta=viol
				 else
					  call funct(n,z,fzdelta)
					  violzdelta=viol
				 endif			      
				
				 nf=nf+1

				 if(iprint.ge.1) then
					  write(*,*) ' fzex=',fzdelta,'  alfaex=',alfaex  
					  write(1,*) ' fzex=',fzdelta,'  alfaex=',alfaex
				 endif
				 if(iprint.ge.2) then
					  do i=1,n
						 write(*,*) ' z(',i,')=',z(i)
						 write(1,*) ' z(',i,')=',z(i)
					  enddo
				 endif

				 fpar= f-gamma*alfaex*alfaex

				 if(fzdelta.lt.fpar) then

					 fz=fzdelta
                     violz=violzdelta
					 alfa=alfaex

				 else               
					 alfa_d(j)=delta*alfa
			         if(iprint.ge.1) then
				         write(*,*) ' accetta punto fz =',fz,'   alfa =',alfa
				         write(1,*) ' accetta punto fz =',fz,'   alfa =',alfa
			         endif
					 return
				 end if

		     enddo
		  else      

			 d(j)=-d(j)
			 ifront=0

			 if(iprint.ge.1) then
				   write(*,*) ' direzione opposta'
				   write(1,*) ' direzione opposta'
				   write(*,*) ' j =',j,'    d(j) =',d(j)
				   write(1,*) ' j =',j,'    d(j) =',d(j)
			 endif

		  endif      
			  
	  enddo     

	  if(i_corr_fall.eq.2) then
			 alfa_d(j)=alfa_d(j)
	  else
			 alfa_d(j)=delta*alfa_d(j)
	  end if

	  alfa=0.d0

	  if(iprint.ge.1) then
			write(*,*) ' fallimento direzione'
			write(1,*) ' fallimento direzione'
	  endif

	  return      
	  
      end

!     ********************************************************
 
      subroutine linesearchbox_discr(n,eta,index_int,step,x,f,d,alfa,alfa_d,z,fz,i_corr,num_fal,&
                                 alfa_max,i_corr_fall,iprint,bl,bu,ni,nf,discr_change,flag_fail)
      
      use scalatura_var
	  use vincoli
      implicit none

      integer :: n,i_corr,nf
      integer :: i,j
      integer :: ni,num_fal
      integer :: iprint,i_corr_fall
	  integer :: ifront,ielle
	  integer :: index_int(n),flag_fail(n)
      real*8 :: x(n),d(n),alfa_d(n),z(n),bl(n),bu(n),step(n)
      real*8 :: f,alfa,alfa_max,alfaex, fz,gamma, gamma_int,eta
      real*8 :: delta,delta1,fpar,fzdelta,violzdelta
	  logical:: discr_change, test

	  
      gamma_int=1.d-0 

      delta =0.5d0
      delta1 =0.5d0

      i_corr_fall=0

	  ifront=0


      j=i_corr


	  if(iprint.ge.1) then
			   write(*,*) 'variabile discreta  j =',j,'    d(j) =',d(j),' alfa=',alfa_d(j)
			   write(1,*) 'variabile discreta  j =',j,'    d(j) =',d(j),' alfa=',alfa_d(j)
	  endif

	  test=.true.

      if(alfa_d(i_corr).eq.step(i_corr)) then  
        
		  do i=1,n
		  
		    if((flag_fail(i).eq.0)) then

		      test=.false.
			  exit

	        end if

		  enddo

          if(test) then

		     alfa=0.d0

		     if(iprint.ge.1) then
			    write(*,*) ' direzione gia analizzata'
			    write(1,*) ' direzione gia analizzata'
		     endif

             return
          endif
                  
	   end if
      
	 
	   do ielle=1,2

		   if(d(j).gt.0.d0) then

				if( ( ( bu(j)-x(j)-alfa_d(j) ) ).lt.0.d0 ) then  
				   alfa=dble(floor( (bu(j)-x(j))/step(j) ))*step(j)
				   ifront=1
				   if (alfa.eq.0.d0) then 
				      d(j)=-d(j)
					  ifront=0
				      cycle
				   endif
                else
				   alfa=alfa_d(j)
				end if		

		   else

				if( ((x(j)-alfa_d(j)-bl(j))).lt.0.0d0 ) then
				   alfa=dble(floor( (x(j)-bl(j))/step(j) ))*step(j)
				   ifront=1
				   if (alfa.eq.0.d0) then
				      d(j)=-d(j)
					  ifront=0
				      cycle
				   endif
				 else
				   alfa=alfa_d(j)
				endif

		   endif

           alfaex=alfa

		   z(j) = x(j)+alfa*d(j)
    
		   if(scalatura) then
			  do i=1,n
				  x_orig(i)=z(i)*scala(i)
			  enddo
			  call funct(n,x_orig,fz)
			  violz=viol
		   else
			  call funct(n,z,fz)
			  violz=viol
		   endif

		   nf=nf+1

		   if(iprint.ge.1) then
				write(*,*) ' fz =',fz,'   alfa =',alfa
				write(1,*) ' fz =',fz,'   alfa =',alfa
		   endif
		   if(iprint.ge.2) then
			   do i=1,n
				  write(*,*) ' z(',i,')=',z(i)
				  write(1,*) ' z(',i,')=',z(i)
			   enddo
		   endif

		   fpar= f-gamma_int*eta


		   if(fz.lt.fpar) then
			  
			  discr_change = .true.


			  do 
                  if(ifront.eq.1) then 

                     if(iprint.ge.1) then
				              write(*,*) ' accetta punto sulla frontiera fz =',fz,'   alfa =',alfa
				              write(1,*) ' accetta punto sulla frontiera fz =',fz,'   alfa =',alfa
			         endif

				     return

				  endif

				  if(d(j).gt.0.d0) then
							
					 if((bu(j)-x(j)-2.0d0*alfa ).lt.(0.0d0)) then

					    alfaex=dble(floor( (bu(j)-x(j))/step(j) ))*step(j)
				        ifront=1

				        if (alfaex.le.alfa) then
						 
						   alfa_d(j)=max(step(j),max(dble(floor((alfa/2.0d0)/step(j)+0.5d0)),1.d0)*step(j))
			               if(iprint.ge.1) then
				              write(*,*) ' accetta punto quasi frontiera fz =',fz,'   alfa =',alfa
				              write(1,*) ' accetta punto quasi frontiera fz =',fz,'   alfa =',alfa
			               endif

						   return

					    endif
						 
					  else

					     alfaex=alfa*2.0d0						
					
					  end if

				   else

					  if(( x(j)-2.0d0*alfa-bl(j) ).lt.(0.0d0)) then

					      alfaex=dble(floor( (x(j)-bl(j))/step(j) ))*step(j)
						  ifront=1

						  if (alfaex.le.alfa) then
						 
						 alfa_d(j)=max(step(j),max(dble(floor((alfa/2.0d0)/step(j)+0.5d0)),1.d0)*step(j))
			                 if(iprint.ge.1) then
				               write(*,*) ' accetta punto quasi frontiera fz =',fz,'   alfa =',alfa
				               write(1,*) ' accetta punto quasi frontiera fz =',fz,'   alfa =',alfa
			                 endif

						     return

						  endif
						 
					  else

					      alfaex=alfa*2.0d0						
					
					  end if

				   endif
						 
				   z(j) = x(j)+alfaex*d(j) 
				   
     
				   if(scalatura) then
					  do i=1,n
						 x_orig(i)=z(i)*scala(i)
					  enddo
					  call funct(n,x_orig,fzdelta)
					  violzdelta=viol
				   else
					  call funct(n,z,fzdelta)
					  violzdelta=viol
				   endif			      
				
				   nf=nf+1

				   if(iprint.ge.1) then
					  write(*,*) ' fzex=',fzdelta,'  alfaex=',alfaex  
					  write(1,*) ' fzex=',fzdelta,'  alfaex=',alfaex
				   endif
				   if(iprint.ge.2) then
					  do i=1,n
						 write(*,*) ' z(',i,')=',z(i)
						 write(1,*) ' z(',i,')=',z(i)
					  enddo
				   endif

				   fpar= f-gamma_int*eta

				   if(fzdelta.lt.fpar) then

					  fz=fzdelta
					  violz=violzdelta
					  alfa=alfaex

				   else               
					   alfa_d(j)=max(step(j),max(dble(floor((alfa/2.0d0)/step(j)+0.5d0)),1.d0)*step(j))
			           if(iprint.ge.1) then
				         write(*,*) ' accetta punto  fz =',fz,'   alfa =',alfa
				         write(1,*) ' accetta punto  fz =',fz,'   alfa =',alfa
			           endif

					  return
				   end if

				enddo

			 else 

				d(j)=-d(j)
				ifront=0

				if(iprint.ge.1) then
				   write(*,*) ' direzione opposta'
				   write(1,*) ' direzione opposta'
				   write(*,*) ' j =',j,'    d(j) =',d(j)
				   write(1,*) ' j =',j,'    d(j) =',d(j)
				endif

			 endif
			  
		  enddo

		  alfa_d(j)=max(step(j),max(dble(floor((alfa/2.0d0)/step(j)+0.5d0)),1.d0)*step(j))

		  alfa=0.d0
		  if(iprint.ge.1) then
			  write(*,*) ' fallimento direzione'
			  write(1,*) ' fallimento direzione'
		  endif

		  return
	  	     
      end


