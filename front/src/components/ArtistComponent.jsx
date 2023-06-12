import React, { useState, useEffect } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSave } from '@fortawesome/free-solid-svg-icons';
import Alert from './Alert';
import BackendService from '../services/BackendService';
import { useParams, useNavigate } from 'react-router-dom';

const ArtistComponent = () => {
  const { id } = useParams(); // Получаем параметр id из URL
  const navigate = useNavigate();

  const [artist, setArtist] = useState({});
  const [showAlert, setShowAlert] = useState(false);

  useEffect(() => {
    retrieveArtist();
  }, []);

  const retrieveArtist = () => {
    BackendService.retrieveArtist(id)
      .then((resp) => {
        setArtist(resp.data);
      })
      .catch((error) => {
        console.error('Ошибка при получении данных артиста:', error);
      });
  };

  const updateArtist = () => {
    BackendService.updateArtist(artist)
      .then(() => {
        navigate('/artists');
      })
      .catch((error) => {
        console.error('Ошибка при обновлении артиста:', error);
        setShowAlert(true);
      });
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setArtist((prevArtist) => ({
      ...prevArtist,
      [name]: value,
    }));
  };

  const closeAlert = () => {
    setShowAlert(false);
  };

  return (
    <div className="m-4">
      <h3>Артист: {artist.name}</h3>
      <div className="row my-2">
        <div className="col">
          <label htmlFor="name">Имя артиста:</label>
          <input
            type="text"
            id="name"
            name="name"
            value={artist.name || ''}
            onChange={handleInputChange}
            className="form-control"
          />
        </div>
        <div className="col">
          <label htmlFor="name">Возраст артиста:</label>
          <input
            type="number"
            id="age"
            name="age"
            value={artist.age || ''}
            onChange={handleInputChange}
            className="form-control"
          />
        </div>
      </div>
      <div className="row my-2">
        <div className="col">
          <button className="btn btn-primary" onClick={updateArtist}>
            <FontAwesomeIcon icon={faSave} /> Сохранить
          </button>
        </div>
      </div>
      <Alert
        title="Ошибка"
        message="Произошла ошибка при обновлении артиста."
        ok={closeAlert}
        close={closeAlert}
        modal={showAlert}
      />
    </div>
  );
};

export default ArtistComponent;
