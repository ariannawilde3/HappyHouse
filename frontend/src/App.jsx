import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Login from './pages/Login.jsx';
import Signup from './pages/Signup.jsx';  // ADD THIS LINE
import Forum from './pages/Forum.jsx';
import PostViewing from './pages/PostViewing.jsx';
import MakePost from './pages/MakePost.jsx';
import Chat from './pages/Chat.jsx';
import Pins from './pages/Pins.jsx';
import Polls from './pages/Polls.jsx';
import CreatePoll from './pages/CreatePoll.jsx';
import PGCMake from './pages/PGCMake.jsx';
import PGCSettings from './pages/PGCSettings.jsx';
import PGCCreated from './pages/PGCCreated.jsx';
import PGCJoined from './pages/PGCJoined.jsx';
import ProfilePage from './pages/ProfilePage.jsx';
import EditProfilePage from './pages/EditProfilePage.jsx';
import GuestSettingsPage from './pages/GuestSettingsPage.jsx';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path='/' element={<Login />} />
        <Route path='/signup' element={<Signup />} />
        <Route path='/neighborhood' element={<Forum />} />
        <Route path='/viewPost' element={<PostViewing />} />
        <Route path='/makePost' element={<MakePost />} />
        <Route path='/house' element={<Chat />} />
        <Route path='/pins' element={<Pins />} />
        <Route path='/polls' element={<Polls />} />
        <Route path='/makePoll' element={<CreatePoll />} />
        <Route path='/makeHouse' element={<PGCMake />} />
        <Route path='/houseSettings' element={<PGCSettings />} />
        <Route path='/houseCreated' element={<PGCCreated />} />
        <Route path='/houseJoined' element={<PGCJoined />} />
        <Route path='/profile' element={<ProfilePage />} />
        <Route path='/editProfile' element={<EditProfilePage />} />
        <Route path='/guestProfile' element={<GuestSettingsPage />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
