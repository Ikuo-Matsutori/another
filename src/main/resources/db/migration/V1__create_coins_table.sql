CREATE TABLE coins (
    id SERIAL PRIMARY KEY,
           ticker_symbol VARCHAR(32) ,
           quantity NUMERIC,
           unit_price NUMERIC,
           total_price NUMERIC,
           wallet VARCHAR(32)
);