Preprocessing operations for CSVs generated by data-extract.py:
    - Re-order and rename columns per the data spec file
    - Ensure readable times have correct zero-padding
    - Sort values by the first column
    - Generate summary reports about collected data
        - Expected data sources present/absent
        - Time profile for each present data source
            - Start time
            - End time
            - Duration
            - Median gap size
            - Mean gap size
            - Maximum gap size and time
            - Batching availability (for accelerometer and gyroscope)
        - (TODO) Coverage charts (i.e., presence/absence of measurements for each 5 minute chunk)

REQUIRES
- Python 2.7.x
- pandas module (http://pandas.pydata.org/)

USAGE
    Basic use case: in-place preprocessing of extracted data
    python preprocess.py <path to extracted data>

    Store preprocessed copies in custom output path
    python preprocess.py <path to extracted data> --out-path <custom output path>

    Use custom data spec file
    python preprocess.py <path to extracted data> --spec-file <custom spec file>

    Use custom coverage chunk
    python preprocess.py <path to extracted data> --timestep <integer number of minutes>
