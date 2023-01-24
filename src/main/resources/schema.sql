INSERT INTO USERS(id,username,city,"STATE",zipcode,peanutInterest,eggInterest,dairyInterest)
VALUES (1,'JonnyCage','New York','NY',10001,false,true,true);


INSERT INTO RESTAURANTS(id,name,city,"STATE",zipcode,peanutAverage,eggAverage,dairyAverage)
VALUES (1,'Pizza Place','New York','NY',10001,4.3,3.1,2.0);

INSERT INTO DINING_REVIEWS(id,username,restaurantId,peanutScore,eggScore,dairyScore,commentary,status)
VALUES (1,'JonnyCage',1,1.3,4.3,3.1,'Delicious food!!!','PENDING');

-- Add some accepted and rejected reviews
INSERT INTO DINING_REVIEWS(id,username,restaurantId,peanutScore,eggScore,dairyScore,commentary,status)
VALUES (2,'JonnyCage',1,1.3,4.3,3.1,'Delicious food!!!','ACCEPTED');
INSERT INTO DINING_REVIEWS(id,username,restaurantId,peanutScore,eggScore,dairyScore,commentary,status)
VALUES (3,'BruceLee',1,1.3,4.3,3.1,'Delicious food!!!','PENDING');
INSERT INTO DINING_REVIEWS(id,username,restaurantId,peanutScore,eggScore,dairyScore,commentary,status)
VALUES (4,'JonnyCage',1,1.3,4.3,3.1,'Delicious food!!!','ACCEPTED');
