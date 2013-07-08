/*patrones de turnos*/

param npat;
param hor;

set H := 1..hor;
set J := 1..npat;

param a{j in J,h in H};
/*param HH;*/
param R{h in H};
var x{j in J}, >=0;
var y{h in H}, >=0;
var z, >=0;

minimize obj:
	sum{j in J} x[j] + 0.01*z;

s.t. cover {h in H}:
	sum{j in J} a[j,h]*x[j] = R[h] + y[h];

/*s.t. personal :
	sum {j in J} x[j] <= HH;
*/
s.t. my {h in H}:
	z >= y[h];

end;

