import numpy as np
import pandas as pd
from itertools import chain
from collections import Counter

import tensorflow as tf
from tensorflow import keras
from tensorflow.keras import layers
from tensorflow.keras.layers import Input, Dense, Activation,Dropout
from tensorflow.keras.models import Model
from sklearn.metrics import mean_squared_error
from math import sqrt
import tensorflow_docs as tfdocs
import tensorflow_docs.plots
import tensorflow_docs.modeling
from tensorflow import feature_column

import matplotlib.pyplot as plt
import seaborn as sns
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import StandardScaler

df = pd.read_csv('ramen-ratings.csv')

# Replace companies that appear only once with "Other"
counts = df['Brand'].value_counts()
unique = counts[counts == 1].index
df.loc[df['Brand'].isin(unique), 'Brand'] = 'Other'

# Find the 100 most common words used in the Variety column
variety = df['Variety']
all_words = list(chain(*[x.split() for x in variety.tolist()]))
most_common_counts = Counter(all_words).most_common(100)
most_common = [x[0] for x in most_common_counts]

# Add columns to represent the presence/absence of each common word
for word in most_common:
    df[word] = 0

for i, row in df.iterrows():
    words = [x for x in row['Variety'].split() if x in most_common]
    for word in words:
        df.at[i, word] = 1

# Ignore
df.pop('Review #')
df.pop('Variety')
df.pop('Top Ten')

df['Brand'] = pd.Categorical(df['Brand'])
df['Brand'] = df.Brand.cat.codes

df['Style'] = pd.Categorical(df['Style'])
df['Style'] = df.Style.cat.codes

df['Country'] = pd.Categorical(df['Country'])
df['Country'] = df.Country.cat.codes

df['Stars'] = pd.Categorical(df['Stars'])
df['Stars'] = df.Stars.cat.codes

cols = df.columns.tolist()
cols.remove('Stars')
cols.append('Stars')
df = df[cols]
df.to_csv('test.csv', index=False)

X = df.iloc[:, 0:-1].values
Y = df.iloc[:, -1].values

X_train, X_test, y_train, y_test = train_test_split(X, Y, test_size=0.2, random_state=0)

sc = StandardScaler()
X_train = sc.fit_transform(X_train)
X_test = sc.transform(X_test)

input_layer = Input(shape=(X.shape[1],))
dense_layer_1 = Dense(100, activation='relu')(input_layer)
dense_layer_2 = Dense(50, activation='relu')(dense_layer_1)
dense_layer_3 = Dense(25, activation='relu')(dense_layer_2)
output = Dense(1)(dense_layer_3)

model = Model(inputs=input_layer, outputs=output)
model.compile(loss="mean_squared_error" , optimizer="adam", metrics=["mean_squared_error"])

history = model.fit(X_train, y_train, batch_size=2, epochs=100, verbose=1, validation_split=0.2)

pred_train = model.predict(X_train)
print(np.sqrt(mean_squared_error(y_train,pred_train)))

pred = model.predict(X_test)
print(np.sqrt(mean_squared_error(y_test,pred)))
# # Tensorflow
# df['Brand'] = pd.Categorical(df['Brand'])
# df['Brand'] = df.Brand.cat.codes
# #
# df['Style'] = pd.Categorical(df['Style'])
# df['Style'] = df.Style.cat.codes
# #
# df['Country'] = pd.Categorical(df['Country'])
# df['Country'] = df.Country.cat.codes
# #
# df['Stars'] = pd.Categorical(df['Stars'])
# df['Stars'] = df.Stars.cat.codes
# # df = pd.get_dummies(df, prefix='', prefix_sep='')
# #
# # df.to_csv('test.csv', index=False)
# print(df.head())
#
# train = df.sample(frac=0.8,random_state=0)
# test = df.drop(train.index)
# sns.pairplot(train[["Stars", "Brand", "Style", "Country"]], diag_kind="kde")
#
# train_stats = train.describe()
# train_stats.pop("Stars")
# train_stats = train_stats.transpose()
#
# print(train_stats)
#
# train_labels = train.pop('Stars')
# test_labels = test.pop('Stars')
#
#
# def norm(x):
#     return (x - train_stats['mean']) / train_stats['std']
#
#
# normed_train_data = norm(train)
# normed_test_data = norm(test)
#
# # Build model
# def build_model():
#     model = keras.Sequential([
#         layers.Dense(64, activation='relu', input_shape=[len(train.keys())]),
#         layers.Dense(64, activation='relu'),
#         layers.Dense(1)])
#
#     optimizer = tf.keras.optimizers.RMSprop(0.001)
#     model.compile(loss='mse', optimizer=optimizer, metrics=['mae', 'mse'])
#     return model
#
#
# model = build_model()
#
# # Inspect model
# model.summary()
# example_batch = normed_train_data[:10]
# example_result = model.predict(example_batch)
#
# # Train model
# EPOCHS = 1000
#
# history = model.fit(
#   normed_train_data, train_labels,
#   epochs=EPOCHS, validation_split = 0.2, verbose=0,
#   callbacks=[tfdocs.modeling.EpochDots()])
# hist = pd.DataFrame(history.history)
# hist['epoch'] = history.epoch
# hist.tail()
#
#
# plotter = tfdocs.plots.HistoryPlotter(smoothing_std=2)
#
# plotter.plot({'Basic': history}, metric = "mae")
# plt.ylim([0, 10])
# plt.ylabel('MAE [Stars]')
#
#
# plotter.plot({'Basic': history}, metric = "mse")
# plt.ylim([0, 20])
# plt.ylabel('MSE [Stars^2]')
#
#
# # Predict
# test_predictions = model.predict(normed_test_data).flatten()
#
# a = plt.axes(aspect='equal')
# plt.scatter(test_labels, test_predictions)
# plt.xlabel('True Values [Stars]')
# plt.ylabel('Predictions [Stars]')
# lims = [0, 50]
# plt.xlim(lims)
# plt.ylim(lims)
# _ = plt.plot(lims, lims)
# error = test_predictions - test_labels
# plt.hist(error, bins = 25)
# plt.xlabel("Prediction Error [Stars]")
# _ = plt.ylabel("Count")
# plt.show()
#
#
# # Generate predictions from the model
# expected = ['Setosa', 'Versicolor', 'Virginica']
# predict_x = {
#     'SepalLength': [5.1, 5.9, 6.9],
#     'SepalWidth': [3.3, 3.0, 3.1],
#     'PetalLength': [1.7, 4.2, 5.4],
#     'PetalWidth': [0.5, 1.5, 2.1],
# }
# predict_x = {
#     'Brand': df['Brand'],
#     'Brand'
# }
#
# def input_fn(features, batch_size=256):
#     """An input function for prediction."""
#     # Convert the inputs to a Dataset without labels.
#     return tf.data.Dataset.from_tensor_slices(dict(features)).batch(batch_size)
#

