Sets

h horas total horizonte de planificación
$include "_h.txt"


j horas que conforman un turno
$include "_j.txt";

*Subsets
i(h) horas de inicios disponibles
$include "_i.txt";

rest(j) horas posibles del descanso
$include "_rest.txt";

alias (h,hh);

Parameter R(h)
$include "_Rh.txt";
;

Parameter

HH_disponibles    total turnos disponibles personas*turnos en el mes      /10/
;

Parameter
orden(h);

loop (h, orden(h) = ord(h) );

display h,i,R,rest;

Parameter NS nivel de servicio exigido /0.9/

Variables
         z
         x(h,j) operarios empesando turno
         d(h)    sobredotación del momento
         y(h)    operarios trabajando
         max_s   máxima subdotación
         s(h)    subdotación en la hora h
;
integer variable x;
positive variable d,s;

equations
         fobj
         cover(h)
         activos(h)
         personal
         maxim(h)
;
fobj..
*         z =e= sum(i, sum(j, x(i,j)))  + sum(h, 10*d(h));
         z =e= max_s + 0.001*sum(h, d(h))
;
cover(h)..
         sum(i$( (orden(i) >= max(ord(h) - card(j) +1, 1 )) and (orden(i) <= min(ord(h), card(h)-card(j)+1 ) ) ) ,
                 sum(j$(( ord(j) > (ord(h)-orden(i) + 1) ) or ( ord(j) < (ord(h)-orden(i)+1) )) ,
*version con salida variable                                 sum(j$(ord(j) < 4 and ord(j) >= 5),
                 x(i,j) ) ) =e= y(h);

activos(h)..
         y(h) =e= R(h) + d(h) - s(h) ;

personal(h)..
         sum(i, sum(j, x(i,j) ) ) =l= HH_disponibles ;
maxim(h)..
         s(h) =l= max_s ;



*set las horas que no pueden tener descanso

loop(h,
*        loop(j$(ord(j) < 4 or ord(j) > 5),
         loop(j$(not rest(j)),
                x.fx(h,j) = 0
         )
);

Model fase_1 /all/

solve fase_1 minimizing z using MIP;


Parameter per_start(h);
loop (h,
per_start(h) = sum(j, x.l(h,j) ));

file salida /z_dotacion.txt/;
put salida ;
loop (h,
         put @1, h.tl, @20, per_start(h) /
         )
;

file salida2 /z_sobredotacion.txt/;
put salida2 ;
loop (h,
         put @1, h.tl, @20, d.l(h) /
         )
;

parameter resting(h)
loop (h,
         resting(h) = sum(i$(orden(i) <= ord(h)), sum(j$(ord(h)=orden(i)+ord(j)-1), x.l(i,j) )) );

file salida3 /z_descansando.txt/;
put salida3 ;
loop (h,
         put @1, h.tl, @20, resting(h) /
         )
;

file salida4 /z_onduty.txt/;
put salida4;
loop (h,
         put @1, h.tl, @20, y.l(h) / )
;

file salida5 /z_personalpatio.txt/;
put salida5;
loop (h,
         put @1, h.tl, @20, (y.l(h) + resting(h)) /)
;
