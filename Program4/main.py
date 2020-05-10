import pandas as pd
from itertools import chain
from collections import Counter

df = pd.read_csv('ramen-ratings.csv')

# Replace companies that appear only once with "Other"
counts = df['Brand'].value_counts()
unique = counts[counts == 1].index
df.loc[df['Brand'].isin(unique), 'Brand'] = 'Other'

# Find the 100 most common words used in the Variety column
variety = df['Variety']
words = list(chain(*[x.split() for x in variety.tolist()]))
most_common_counts = Counter(words).most_common(100)
most_common = [x[0] for x in most_common_counts]

# Keep only the most common words for classification
df['Variety'] = df['Variety'].apply(lambda s: ' '.join([x for x in s.split() if x in most_common]))

df.to_csv('ramen-ratings-cleaned.csv', index=False)
