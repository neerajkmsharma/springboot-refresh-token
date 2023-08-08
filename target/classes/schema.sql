CREATE TABLE CUSTOMER_DETAIL (
  CUST_ID BIGINT PRIMARY KEY, 
  CUST_NAME VARCHAR2(50)
);
CREATE TABLE TRANSACTION_DETAIL (
  TRANS_ID BIGINT PRIMARY KEY, 
  CUST_ID BIGINT, 
  TRANS_DATE DATE, 
  TRANS_AMOUNT NUMERIC(20, 2)
);

INSERT INTO CUSTOMER_DETAIL(CUST_ID, CUST_NAME) 
values 
  (111001, 'Jack Smith');
INSERT INTO CUSTOMER_DETAIL(CUST_ID, CUST_NAME) 
values 
  (111002, 'Adam Gilchrist');
INSERT INTO CUSTOMER_DETAIL(CUST_ID, CUST_NAME) 
values 
  (111003, 'Glenn McGrath');
INSERT INTO TRANSACTION_DETAIL(
  TRANS_ID, CUST_ID, TRANS_DATE, TRANS_AMOUNT
) 
VALUES 
  (
    11110001, 111001, '2023-07-14', 140.00
  );
INSERT INTO TRANSACTION_DETAIL(
  TRANS_ID, CUST_ID, TRANS_DATE, TRANS_AMOUNT
) 
VALUES 
  (
    11110002, 111001, '2023-06-15', 86.00
  );
INSERT INTO TRANSACTION_DETAIL(
  TRANS_ID, CUST_ID, TRANS_DATE, TRANS_AMOUNT
) 
VALUES 
  (
    11110003, 111001, '2023-06-16', 160.00
  );
INSERT INTO TRANSACTION_DETAIL(
  TRANS_ID, CUST_ID, TRANS_DATE, TRANS_AMOUNT
) 
VALUES 
  (
    11110004, 111001, '2023-05-18', 120.00
  );
INSERT INTO TRANSACTION_DETAIL(
  TRANS_ID, CUST_ID, TRANS_DATE, TRANS_AMOUNT
) 
VALUES 
  (
    11110006, 111002, '2023-07-20', 112.00
  );
INSERT INTO TRANSACTION_DETAIL(
  TRANS_ID, CUST_ID, TRANS_DATE, TRANS_AMOUNT
) 
VALUES 
  (
    11110007, 111002, '2023-07-21', 89.00
  );
INSERT INTO TRANSACTION_DETAIL(
  TRANS_ID, CUST_ID, TRANS_DATE, TRANS_AMOUNT
) 
VALUES 
  (
    11110008, 111002, '2023-06-22', 100.00
  );
INSERT INTO TRANSACTION_DETAIL(
  TRANS_ID, CUST_ID, TRANS_DATE, TRANS_AMOUNT
) 
VALUES 
  (
    11110009, 111002, '2023-05-03', 155.00
  );
INSERT INTO TRANSACTION_DETAIL(
  TRANS_ID, CUST_ID, TRANS_DATE, TRANS_AMOUNT
) 
VALUES 
  (
    11110010, 111002, '2023-05-06', 78.00
  );
INSERT INTO TRANSACTION_DETAIL(
  TRANS_ID, CUST_ID, TRANS_DATE, TRANS_AMOUNT
) 
VALUES 
  (
    11110011, 111003, '2023-07-11', 105.00
  );
INSERT INTO TRANSACTION_DETAIL(
  TRANS_ID, CUST_ID, TRANS_DATE, TRANS_AMOUNT
) 
VALUES 
  (
    11110012, 111003, '2023-06-13', 66.00
  );
INSERT INTO TRANSACTION_DETAIL(
  TRANS_ID, CUST_ID, TRANS_DATE, TRANS_AMOUNT
) 
VALUES 
  (
    11110013, 111003, '2023-06-15', 55.00
  );
INSERT INTO TRANSACTION_DETAIL(
  TRANS_ID, CUST_ID, TRANS_DATE, TRANS_AMOUNT
) 
VALUES 
  (
    11110014, 111003, '2023-06-19', 365.00
  );
INSERT INTO TRANSACTION_DETAIL(
  TRANS_ID, CUST_ID, TRANS_DATE, TRANS_AMOUNT
) 
VALUES 
  (
    11110015, 111003, '2023-05-20', 785.00
  );
INSERT INTO TRANSACTION_DETAIL(
  TRANS_ID, CUST_ID, TRANS_DATE, TRANS_AMOUNT
) 
VALUES 
  (
    11110016, 111003, '2023-05-24', 65.00
  );
 
  
COMMIT;
